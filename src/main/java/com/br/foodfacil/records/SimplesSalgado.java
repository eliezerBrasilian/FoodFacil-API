package com.br.foodfacil.records;


import java.util.List;

public record SimplesSalgado(
        String id,
        String observacao,
        List<String> sabores,
        int quantidade
) {
}
