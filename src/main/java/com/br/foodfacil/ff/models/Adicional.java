package com.br.foodfacil.ff.models;

import com.br.foodfacil.ff.dtos.AdicionalDto;
import com.br.foodfacil.ff.enums.Disponibilidade;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "adicionais")
public class Adicional {
    @Id
    String id;
    float preco;
    String imagem;
    String titulo;
    String descricao;
    Disponibilidade disponibilidade;

    public Adicional(AdicionalDto adicionalDto){
        this.preco = adicionalDto.preco();
        this.imagem = adicionalDto.imagem();
        this.titulo = adicionalDto.titulo();
        this.descricao = adicionalDto.descricao();
        this.disponibilidade = adicionalDto.disponibilidade();
    }
}
