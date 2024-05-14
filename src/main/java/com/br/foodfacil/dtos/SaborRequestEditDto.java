package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

public record SaborRequestEditDto(
        @NotNull String nome,
        @NotNull Disponibilidade disponibilidade
) {
}
