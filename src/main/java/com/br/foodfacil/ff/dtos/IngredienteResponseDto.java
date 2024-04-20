package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.Disponibilidade;

public record IngredienteResponseDto(
        String id,
        String nome,
        String imagem,
        float preco,
        long createdAt,
        Disponibilidade disponibilidade
) {
}
