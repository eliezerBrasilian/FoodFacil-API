package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.Categoria;
import com.br.foodfacil.ff.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SalgadoResponseDto(
        @NotNull String id,
        @NotNull String name,
        @NotNull Categoria categoria,
        @NotNull String description,
        @NotNull float price,
        @NotNull String image,
        @NotNull boolean inOffer,
        @NotNull float priceInOffer,
        @NotNull Disponibilidade disponibilidade,
        @NotNull List<SaborDto> saborDtos
) {
}
