package com.fiap.posTech.parquimetro.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("pagamento")
@Data
public class Pagamento {

    @Id
    private String id;

    private LocalDateTime dataPagamento;
    private double valor;

    @Enumerated(EnumType.STRING)
    private EnumPagamento tipoPagamento;

    @DBRef
    private Pessoa pessoa;
}
