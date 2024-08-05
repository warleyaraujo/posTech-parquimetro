package com.fiap.posTech.parquimetro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document("pessoa")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    private String id;
    @NotBlank(message = "O nome não pode estar em branco.")
    private String nome;
    @Email(message = "E-mail inválido.")
    private String email;
    @CPF(message = "CPF inválido.")
    private String cpf;
    @Size(min = 9, max = 9, message = "RG deve ter exatamente 9 caracteres.")
    private String rg;
    private String celular;
    private LocalDate dataNascimento;

    @Column(name = "forma_pagamento")
    @Enumerated(EnumType.STRING)
    private EnumPagamento formaPagamento;

    @DBRef
    private Endereco endereco;

    @JsonIgnore
    @DBRef(lazy = true)
    private List<Veiculo> veiculos;

    @JsonIgnore
    @DBRef
    private List<Pagamento> pagamentos;

    @JsonIgnore
    @Version
    private Long version;

    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculos == null) {
            veiculos = new ArrayList<>();
        }
        veiculos.add(veiculo);
        veiculo.setPessoa(this);
    }

    public void definirFormaPagamento(EnumPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void adicionarPagamento(Pagamento pagamento) {
        if (pagamentos == null) {
            pagamentos = new ArrayList<>();
        }
        pagamentos.add(pagamento);
    }

}
