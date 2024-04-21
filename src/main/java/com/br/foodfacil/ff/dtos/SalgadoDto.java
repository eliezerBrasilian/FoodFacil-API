package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.Categoria;
import com.br.foodfacil.ff.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SalgadoDto(
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
        List<IngredienteDto> ingredientes,
        String observacao
) {
}
