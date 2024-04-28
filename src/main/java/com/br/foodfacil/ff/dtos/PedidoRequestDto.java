package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.PagamentoStatus;
import com.br.foodfacil.ff.enums.PedidoStatus;
import com.br.foodfacil.ff.enums.Plataforma;
import com.br.foodfacil.ff.enums.TipoDePagamento;
import com.br.foodfacil.ff.records.Address;
import com.br.foodfacil.ff.records.SimplesAdicional;
import com.br.foodfacil.ff.records.SimplesSalgado;

import java.util.List;

public record PedidoRequestDto(
        String userId,
        String userEmail,
        List<SimplesSalgado> salgados,
        List<SimplesAdicional> adicionais ,
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
}
