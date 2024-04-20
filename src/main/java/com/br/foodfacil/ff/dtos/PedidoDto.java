package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.PagamentoStatus;
import com.br.foodfacil.ff.enums.PedidoStatus;
import com.br.foodfacil.ff.enums.Plataforma;

import java.util.List;

public record PedidoDto(
        String compradorId,
        Plataforma plataforma,
        float total,
        long createdAt,
        List<SalgadoDentroDePedido> salgados,
        PedidoStatus status,
        PagamentoStatus pagamentoStatus) {
}
