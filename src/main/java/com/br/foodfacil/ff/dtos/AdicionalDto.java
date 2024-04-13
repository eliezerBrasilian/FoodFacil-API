package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

public record AdicionalDto(
        String imagem,
        @NotNull String titulo,
        @NotNull String descricao,
        @NotNull float preco,
        @NotNull Disponibilidade disponibilidade
) {
}
