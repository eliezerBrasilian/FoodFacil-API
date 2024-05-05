package com.br.foodfacil.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record NotificacaoRequestDTO(
       @NotNull String titulo,
       @NotNull String corpo,
       String imagem,
       Map<String,String> dados) {
}
