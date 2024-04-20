package com.br.foodfacil.ff.models;

import com.br.foodfacil.ff.dtos.AdicionalDto;
import com.br.foodfacil.ff.dtos.IngredienteDto;
import com.br.foodfacil.ff.enums.Disponibilidade;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "ingredientes")
public class Ingrediente {
    @Id
    String id;
    String nome;
    String imagem;
    float preco;
    long createdAt;
    Disponibilidade disponibilidade;

    public Ingrediente(IngredienteDto ingredienteDto){
        this.nome = ingredienteDto.nome();
        this.imagem = ingredienteDto.imagem();
        this.preco = ingredienteDto.preco();
        this.createdAt = ingredienteDto.createdAt();
        this.disponibilidade = ingredienteDto.disponibilidade();
    }
}
