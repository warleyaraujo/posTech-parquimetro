package com.fiap.posTech.parquimetro.service;

import com.fiap.posTech.parquimetro.controller.exception.ControllerNotFoundException;
import com.fiap.posTech.parquimetro.controller.exception.CustomException;
import com.fiap.posTech.parquimetro.model.Pessoa;
import com.fiap.posTech.parquimetro.model.Veiculo;
import com.fiap.posTech.parquimetro.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public Page<Veiculo> findAll(Pageable pageable) {
        return veiculoRepository.findAll(pageable);
    }
    @Transactional
    public Veiculo findById(String id) {
        try {
            return veiculoRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Veiculo não encontrado"));
        } catch (ControllerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Erro ao buscar Veiculo", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
        }
    }

    @Transactional
    public Veiculo findByPlaca(String placa) {
        try {
            return veiculoRepository.findByPlaca(placa).orElseThrow(() -> new ControllerNotFoundException("Veiculo não encontrado"));
        } catch (ControllerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException("Erro ao buscar Veiculo", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
        }
    }

    @Transactional
    public List<Veiculo> findByPessoa(Pessoa pessoa) {
        try {
            return pessoa.getVeiculos();
        } catch (Exception e) {
            throw new CustomException("Erro ao buscar Veiculos do(a): " + pessoa.getNome(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
        }
    }

    @Transactional
    public Veiculo save(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }
    @Transactional
    public Veiculo update(String id, Veiculo updatedVeiculo) {
        try {
            Veiculo veiculo = veiculoRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Veículo não encontrado"));

            veiculo.setModelo(updatedVeiculo.getModelo());
            veiculo.setCor(updatedVeiculo.getCor());
            veiculo.setModelo(updatedVeiculo.getModelo());
            veiculo.setAnoFabrica(updatedVeiculo.getAnoFabrica());
            veiculo.setAnoModelo(updatedVeiculo.getAnoModelo());
            veiculo.setPlaca(updatedVeiculo.getPlaca());

            return veiculoRepository.save(veiculo);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Veiculo com id:" + id + " não encontrado");
        }
    }
    @Transactional
    public void delete(String id) {
        veiculoRepository.deleteById(id);
    }

}
