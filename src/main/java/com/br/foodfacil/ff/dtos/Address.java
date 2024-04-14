package com.br.foodfacil.ff.dtos;

public record Address(
        String rua,
        int numero,
        String bairro,
        String complemento
        ) { }
