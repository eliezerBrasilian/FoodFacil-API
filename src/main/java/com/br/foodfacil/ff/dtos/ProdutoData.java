package com.br.foodfacil.ff.dtos;

import jakarta.validation.constraints.NotNull;

public record ProdutoData(
        @NotNull String titulo,
        @NotNull String descricao,
        @NotNull String valor){}
