package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.Disponibilidade;

public record Acompanhamento(String id,
                             String name,
                             float precoPorUnidade,
                             String image,
                             Disponibilidade disponibilidade
                             ) { }
