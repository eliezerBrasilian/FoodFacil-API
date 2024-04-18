package com.br.foodfacil.ff.dtos;

public record Address(
        String cidade,
        String rua,
        int numero,
        String bairro,
        String complemento
        ) { }
