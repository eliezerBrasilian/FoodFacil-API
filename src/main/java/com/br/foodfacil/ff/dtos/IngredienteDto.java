package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.Disponibilidade;

public record IngredienteDto(
        String nome,
        String imagem,
        float preco,
        long createdAt,
        Disponibilidade disponibilidade
) { }
