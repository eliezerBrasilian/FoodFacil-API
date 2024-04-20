package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.Categoria;
import com.br.foodfacil.ff.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SalgadoDto(
        @NotNull String name,
        @NotNull Categoria categoria,
        @NotNull String description,
        @NotNull float price,
        @NotNull String image,
        @NotNull String imageRetangular,
        @NotNull String imageQuadrada,
        @NotNull boolean inOffer,
        @NotNull float priceInOffer,
        @NotNull Disponibilidade disponibilidade,
        List<IngredienteDto> ingredienteDtos,
        String observacao
) {
}
