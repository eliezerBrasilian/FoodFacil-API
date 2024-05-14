package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

public record SaborRequestDto(
       @NotNull String nome,
       @NotNull Disponibilidade disponibilidade,
       @NotNull long createdAt
) { }
