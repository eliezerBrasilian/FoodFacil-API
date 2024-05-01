package com.br.foodfacil.dtos;

public record AcompanhamentoResumidoResponseDto(
        String nome,
        String descricao,
        float preco,
        int quantidade
){}
