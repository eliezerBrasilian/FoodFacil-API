package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.Categoria;
import com.br.foodfacil.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AcompanhamentoRequestEditDto(
        String imagem,
        @NotNull String nome,
        @NotNull String descricao,
        @NotNull float preco,
        @NotNull Disponibilidade disponibilidade
) {
}
