package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.PagamentoStatus;
import com.br.foodfacil.enums.PedidoStatus;
import com.br.foodfacil.enums.Plataforma;
import com.br.foodfacil.enums.TipoDePagamento;
import com.br.foodfacil.records.Address;

import java.util.List;

public record PedidoRequestDto(
        String userId,
        String userEmail,
        List<SalgadoRequestDto> salgados,
        List<AcompanhamentoRequestDto> acompanhamentos ,
        Address endereco,
        TipoDePagamento pagamentoEscolhido,
        float quantiaReservada,
        Plataforma plataforma,
        String dispositivoToken,
        float total,
        PedidoStatus status,
        PagamentoStatus pagamentoStatus,
        float taxa,
        long createdAt
) {
}
