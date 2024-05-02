package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.PagamentoStatus;
import com.br.foodfacil.enums.PedidoStatus;
import com.br.foodfacil.enums.Plataforma;
import com.br.foodfacil.enums.TipoDePagamento;
import com.br.foodfacil.records.Address;

import java.util.List;

public record PedidoDoUsuarioResponseDto(
        String id,
        String userId,
        List<SalgadoResumidoResponseDto> salgados,
        List<AcompanhamentoResumidoResponseDto> acompanhamentos,
        Address endereco,
        TipoDePagamento pagamentoEscolhido,
        float quantiaReservada,
        float total,
        long createdAt,
        PedidoStatus status,
        PagamentoStatus pagamentoStatus
) {
};

