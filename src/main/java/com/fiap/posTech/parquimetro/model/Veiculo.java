package com.fiap.posTech.parquimetro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("veiculo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

    @Id
    private String id;

    @NotBlank(message = "O Modelo não pode estar em branco.")
    private String modelo;
    @NotBlank(message = "A Cor não pode estar em branco.")
    private String cor;
    @NotBlank(message = "O Ano de Fábrica não pode estar em branco.")
    private String anoFabrica;
    @NotBlank(message = "O Ano do Modelo não pode estar em branco.")
    private String anoModelo;
    @NotBlank(message = "A Placa não pode estar em branco.")
    private String placa;

    @JsonIgnore
    @DBRef
    private Pessoa pessoa;

    @JsonIgnore
    @Version
    private Long version;

}
