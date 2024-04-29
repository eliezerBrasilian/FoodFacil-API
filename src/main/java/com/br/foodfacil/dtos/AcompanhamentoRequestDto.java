package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

public record AcompanhamentoRequestDto(
        @NotNull  String imagem,
        @NotNull String nome,
        @NotNull String descricao,
        @NotNull float preco,
        @NotNull Disponibilidade disponibilidade,
        @NotNull long createdAt
) {
}
