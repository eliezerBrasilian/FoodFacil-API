package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.Disponibilidade;

public record SaborDto(
        String nome,
        String imagem,
        Disponibilidade disponibilidade
) { }
