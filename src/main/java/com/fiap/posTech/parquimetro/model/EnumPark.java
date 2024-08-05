package com.fiap.posTech.parquimetro.model;

public enum EnumPark {
    FIXO("Fixo"),
    HORA("hora");
    private String descricao;

    EnumPark(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static boolean contains(String value) {
        for (EnumPark enumPark : EnumPark.values()) {
            if (enumPark.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}