package com.fiap.posTech.parquimetro.service;

import com.fiap.posTech.parquimetro.controller.exception.ControllerNotFoundException;
import com.fiap.posTech.parquimetro.controller.exception.ParkingException;
import com.fiap.posTech.parquimetro.model.*;
import com.fiap.posTech.parquimetro.repository.EnderecoRepository;
import com.fiap.posTech.parquimetro.repository.PessoaRepository;
import com.fiap.posTech.parquimetro.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
    private final VeiculoRepository veiculoRepository;

    public Page<Pessoa> findAll(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    @Transactional
    public Pessoa findById(String id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public Pessoa save(Pessoa pessoa) {
        Optional.ofNullable(pessoa.getEndereco()).ifPresent(enderecoRepository::save);
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public Pessoa update(String id, Pessoa updatedPessoa) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado"));

        pessoa.setNome(updatedPessoa.getNome());
        pessoa.setEmail(updatedPessoa.getEmail());
        pessoa.setCpf(updatedPessoa.getCpf());
        pessoa.setRg(updatedPessoa.getRg());
        pessoa.setCelular(updatedPessoa.getCelular());
        pessoa.setDataNascimento(updatedPessoa.getDataNascimento());

        Optional.ofNullable(updatedPessoa.getEndereco())
                .ifPresent(e -> updateEndereco(pessoa.getEndereco(), e));

        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public void delete(String id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado"));
        if (pessoa.getVeiculos() != null) {
            for (Veiculo veiculo : pessoa.getVeiculos()) {
                if (veiculo != null) {
                    veiculoRepository.deleteById(veiculo.getId());
                }
            }
        }
        Optional.ofNullable(pessoa.getEndereco())
                .ifPresent(endereco -> enderecoRepository.deleteById(endereco.getId()));

        pessoaRepository.deleteById(id);
    }

    @Transactional
    public Pessoa pagarEstacionamento(String pessoaId, double valor) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado"));

        EnumPagamento formaPagamento = pessoa.getFormaPagamento();

        if (formaPagamento == null) {
            throw new ParkingException("Forma de Pagamento não registrada para o usuário");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setValor(valor);
        pagamento.setTipoPagamento(formaPagamento);
        pagamento.setPessoa(pessoa);

        pessoa.adicionarPagamento(pagamento);

        pessoa = pessoaRepository.save(pessoa);

        return pessoa;
    }

    @Transactional
    public Pessoa definirFormaPagamento(String pessoaId, EnumPagamento formaPagamento) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado"));

        pessoa.definirFormaPagamento(formaPagamento);

        pessoa = pessoaRepository.save(pessoa);

        return pessoa;
    }

    private void updateEndereco(Endereco endereco, Endereco updatedEndereco) {
        endereco.setTipoLogradouro(updatedEndereco.getTipoLogradouro());
        endereco.setLogradouro(updatedEndereco.getLogradouro());
        endereco.setNumero(updatedEndereco.getNumero());
        endereco.setComplemento(updatedEndereco.getComplemento());
        endereco.setBairro(updatedEndereco.getBairro());
        endereco.setCidade(updatedEndereco.getCidade());
        endereco.setUf(updatedEndereco.getUf());
        endereco.setCep(updatedEndereco.getCep());

        enderecoRepository.save(endereco);
    }
}
