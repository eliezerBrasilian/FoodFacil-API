package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.Categoria;
import com.br.foodfacil.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SalgadoRequestDto(
        String id,
        @NotNull String nome,
        @NotNull Categoria categoria,
        @NotNull String descricao,
        @NotNull float preco,
        @NotNull String imagem,
        @NotNull String imagemRetangular,
        @NotNull String imagemQuadrada,
        @NotNull boolean emOferta,
        @NotNull float precoEmOferta,
        @NotNull Disponibilidade disponibilidade,
        @NotNull long createdAt,
        String observacao,
        int quantidade,
        List<String> sabores
) {
}
