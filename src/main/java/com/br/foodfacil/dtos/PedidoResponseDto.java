package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.PagamentoStatus;
import com.br.foodfacil.enums.PedidoStatus;
import com.br.foodfacil.enums.Plataforma;
import com.br.foodfacil.enums.TipoDePagamento;
import com.br.foodfacil.records.Address;

import java.util.List;

public record PedidoResponseDto(
        String id,
        UserResponseDto usuario,
        List<SalgadoResumidoResponseDto> salgados,
        List<AcompanhamentoResumidoResponseDto> acompanhamentos,
        Address endereco,
        TipoDePagamento pagamentoEscolhido,
        float quantiaReservada,
        Plataforma plataforma,
        String dispositivoToken,
        float total,
        long createdAt,
        PedidoStatus status,
        PagamentoStatus pagamentoStatus
) {
};

