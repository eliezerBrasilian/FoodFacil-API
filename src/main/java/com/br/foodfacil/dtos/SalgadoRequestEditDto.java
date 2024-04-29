package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.Categoria;
import com.br.foodfacil.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SalgadoRequestEditDto(
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
        List<String> sabores
) {
}
