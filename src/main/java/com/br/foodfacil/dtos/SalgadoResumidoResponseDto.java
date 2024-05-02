package com.br.foodfacil.dtos;

import java.util.List;

public record SalgadoResumidoResponseDto
        (
                String nome,
                String descricao,
                String imagem,
                float preco,
                String observacao,
                int quantidade, List<String> sabores) {
}
