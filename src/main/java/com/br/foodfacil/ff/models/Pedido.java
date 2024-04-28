package com.br.foodfacil.ff.models;

import com.br.foodfacil.ff.dtos.PedidoRequestDto;
import com.br.foodfacil.ff.enums.PagamentoStatus;
import com.br.foodfacil.ff.enums.PedidoStatus;
import com.br.foodfacil.ff.enums.Plataforma;
import com.br.foodfacil.ff.enums.TipoDePagamento;
import com.br.foodfacil.ff.records.Address;
import com.br.foodfacil.ff.records.SimplesAdicional;
import com.br.foodfacil.ff.records.SimplesSalgado;
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
    private List<SimplesAdicional> adicionais;
    private Address endereco;
    TipoDePagamento pagamentoEscolhido;
    float quantiaReservada;
    private Plataforma plataforma;
    private String dispositivoToken;
    private float total;
    private long createdAt;
    private PedidoStatus status;
    private PagamentoStatus pagamentoStatus;


    public Pedido(PedidoRequestDto pedidoRequestDto){
        this.salgados = pedidoRequestDto.salgados();
        this.adicionais = pedidoRequestDto.adicionais();
        this.endereco = pedidoRequestDto.endereco();
        this.pagamentoEscolhido = pedidoRequestDto.pagamentoEscolhido();
        this.quantiaReservada = pedidoRequestDto.quantiaReservada();
        this.plataforma = pedidoRequestDto.plataforma();
        this.total = pedidoRequestDto.total();
        this.dispositivoToken = pedidoRequestDto.dispositivoToken();
        this.createdAt = pedidoRequestDto.createdAt();
        this.status = pedidoRequestDto.status();
        this.pagamentoStatus = pedidoRequestDto.pagamentoStatus();
    }
}
