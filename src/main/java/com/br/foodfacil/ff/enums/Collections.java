package com.br.foodfacil.ff.enums;

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
