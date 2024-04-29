package com.br.foodfacil.models;

import com.br.foodfacil.dtos.AcompanhamentoRequestDto;
import com.br.foodfacil.enums.Disponibilidade;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "acompanhamentos")
public class Acompanhamento {
    @Id
    private String id;
    private float preco;
    private String imagem;
    private String nome;
    private String descricao;
    private Disponibilidade disponibilidade;
    private long createdAt;

    public Acompanhamento(AcompanhamentoRequestDto acompanhamentoRequestDto){
        this.preco = acompanhamentoRequestDto.preco();
        this.imagem = acompanhamentoRequestDto.imagem();
        this.nome = acompanhamentoRequestDto.nome();
        this.descricao = acompanhamentoRequestDto.descricao();
        this.disponibilidade = acompanhamentoRequestDto.disponibilidade();
        this.createdAt = acompanhamentoRequestDto.createdAt();
    }
}
