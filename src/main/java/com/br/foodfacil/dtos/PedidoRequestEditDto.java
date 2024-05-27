package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.PedidoStatus;
import jakarta.validation.constraints.NotNull;

public record PedidoRequestEditDto(@NotNull PedidoStatus pedidoStatus, String dispositivoToken) {
}
