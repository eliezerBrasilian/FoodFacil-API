package com.br.foodfacil.ff.enums;

public enum Categoria {
    SALGADONOCOPO("Salgado no Copo"),
    RISOLE("Risole"),
    COXINHA("Coxinha"),
    PASTEL("Pastel"),
    HOTDOG("Hot Dog"),
    COMBO("Combo"),
    CONGELADOS("Congelados");

    public final String value;

    Categoria(String descricao){
        this.value = descricao;
    }
}
