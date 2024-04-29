package com.br.foodfacil.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Collections {
    USUARIOS("usuarios"),
    PEDIDOS("pedidos"),
    SALGADOS("salgados"),
    TOKENS("tokens");

    public final String value;
}
