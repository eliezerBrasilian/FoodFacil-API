package com.br.foodfacil.ff.models;

import com.br.foodfacil.ff.dtos.SaborDto;
import com.br.foodfacil.ff.enums.Disponibilidade;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "sabores")
public class Ingrediente {
    @Id
    String id;
    String nome;
    String imagem;
    float preco;
    long createdAt;
    Disponibilidade disponibilidade;

    public Ingrediente(SaborDto saborDto){
        this.nome = saborDto.nome();
        this.imagem = saborDto.imagem();
  /*      this.preco = saborDto.preco();
        this.createdAt = saborDto.createdAt();*/
        this.disponibilidade = saborDto.disponibilidade();
    }
}
