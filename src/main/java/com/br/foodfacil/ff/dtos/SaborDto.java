package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.Disponibilidade;

public record SaborDto(
        String nome,
        String imagem,
        Disponibilidade disponibilidade
) { }
