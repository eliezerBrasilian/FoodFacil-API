package com.br.foodfacil.ff.models;

import com.br.foodfacil.ff.dtos.PedidoDto;
import com.br.foodfacil.ff.dtos.SalgadoDentroDePedido;
import com.br.foodfacil.ff.enums.PagamentoStatus;
import com.br.foodfacil.ff.enums.PedidoStatus;
import com.br.foodfacil.ff.enums.Plataforma;
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
    private String compradorId;
    private Plataforma plataforma;
    private float total;
    private long createdAt;
    private List<SalgadoDentroDePedido> salgados;
    private PedidoStatus status;
    private PagamentoStatus pagamentoStatus;


    public Pedido(PedidoDto pedidoDto){
        this.compradorId = pedidoDto.compradorId();
        this.plataforma = pedidoDto.plataforma();
        this.total = pedidoDto.total();
        this.createdAt = pedidoDto.createdAt();
        this.salgados = pedidoDto.salgados();
        this.status = pedidoDto.status();
        this.pagamentoStatus = pedidoDto.pagamentoStatus();
    }
}
