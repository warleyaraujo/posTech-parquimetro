package com.fiap.posTech.parquimetro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;


@Document("parking")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Park {
    @Id
    private String id;

    private LocalDateTime entrada;
    private LocalDateTime saida;
    private double valorHora;
    private double valorCobrado;


    private EnumPark tipoTempo;

    private String permanencia;
    private Boolean ativa = false;
    private Integer tempoFixo;

    @DBRef
    private Pessoa pessoa;

    @DBRef
    private Veiculo veiculo;

    @JsonIgnore
    @Version
    private Long version;
}
