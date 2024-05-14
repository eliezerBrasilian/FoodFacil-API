package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.Categoria;
import com.br.foodfacil.enums.Disponibilidade;

import java.util.List;

public record SalgadoResponseDto(
        String id,
        String nome,
        Categoria categoria,
        String descricao,
        float preco,
        String imagem,
        String imagemRetangular,
        String imagemQuadrada,
        boolean emOferta,
        float precoEmOferta,
        Disponibilidade disponibilidade,
        long createdAt,
        List<SaborRequestDto> sabores
) {
}
