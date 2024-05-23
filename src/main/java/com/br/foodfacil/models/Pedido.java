package com.br.foodfacil.models;

import com.br.foodfacil.dtos.PedidoRequestDto;
import com.br.foodfacil.enums.PagamentoStatus;
import com.br.foodfacil.enums.PedidoStatus;
import com.br.foodfacil.enums.Plataforma;
import com.br.foodfacil.enums.TipoDePagamento;
import com.br.foodfacil.records.Address;
import com.br.foodfacil.records.SimplesAdicional;
import com.br.foodfacil.records.SimplesSalgado;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "pedidos")
public class Pedido {
    @Id
    private String id;
    private String userId;
    private List<SimplesSalgado> salgados;
    private List<SimplesAdicional> acompanhamentos;
    private Address endereco;
    TipoDePagamento pagamentoEscolhido;
    float quantiaReservada;
    private Plataforma plataforma;
    private String dispositivoToken;
    private float total;
    private PedidoStatus status;
    private PagamentoStatus pagamentoStatus;
    private String chavePix;
    private float taxa;
    private long createdAt;
    private long payedAt;

    public Pedido(PedidoRequestDto pedidoRequestDto, String chavePix){
        this.userId = pedidoRequestDto.userId();
        this.salgados = pedidoRequestDto.salgados();
        this.acompanhamentos = pedidoRequestDto.acompanhamentos();
        this.endereco = pedidoRequestDto.endereco();
        this.pagamentoEscolhido = pedidoRequestDto.pagamentoEscolhido();
        this.quantiaReservada = pedidoRequestDto.quantiaReservada();
        this.plataforma = pedidoRequestDto.plataforma();
        this.total = pedidoRequestDto.total();
        this.dispositivoToken = pedidoRequestDto.dispositivoToken();
        this.createdAt = pedidoRequestDto.createdAt();
        this.status = pedidoRequestDto.status();
        this.pagamentoStatus = pedidoRequestDto.pagamentoStatus();
        this.chavePix = chavePix;
        this.taxa = pedidoRequestDto.taxa();
        this.payedAt = -1;
    }
}
