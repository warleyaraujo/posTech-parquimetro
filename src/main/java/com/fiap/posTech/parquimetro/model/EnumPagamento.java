package com.fiap.posTech.parquimetro.model;

public enum EnumPagamento {
    DINHEIRO("Dinherio"),
    DEBITO("Débito"),
    CREDITO("Crédito"),
    PIX("Pix");

    private String descricao;

    EnumPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static boolean contains(String value) {
        for (EnumPagamento enumPagamento : EnumPagamento.values()) {
            if (enumPagamento.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}

