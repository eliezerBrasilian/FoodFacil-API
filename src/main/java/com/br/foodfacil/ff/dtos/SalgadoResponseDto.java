package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.Categoria;
import com.br.foodfacil.ff.enums.Disponibilidade;
import jakarta.validation.constraints.NotNull;

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
        List<SaborDto> sabores
) {
}
