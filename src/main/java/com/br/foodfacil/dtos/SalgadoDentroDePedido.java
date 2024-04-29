package com.br.foodfacil.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SalgadoDentroDePedido(
        @NotNull String id,
        @NotNull float total,
        @NotNull boolean inOffer,
        List<String> ingredientesIds,
        List<AdicionalSimpleDto> adicionais,
        String observacao
) {
}
