package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.Disponibilidade;

public record IngredienteResponseDto(
        String id,
        String nome,
        String imagem,
        float preco,
        long createdAt,
        Disponibilidade disponibilidade
) {
}
