package com.br.foodfacil.ff.models;

import com.br.foodfacil.ff.dtos.Acompanhamento;
import com.br.foodfacil.ff.dtos.SalgadoDto;
import com.br.foodfacil.ff.enums.Categoria;
import com.br.foodfacil.ff.enums.Disponibilidade;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Document(collection = "salgados")
public class Salgado {
    @Id
    private String id;
    private Categoria categoria;
    private String name;
    private String description;
    private String image;
    private float price;
    private boolean inOffer;
    private float priceInOffer;
    private Disponibilidade disponibilidade;
    private List<Acompanhamento> acompanhamentos;

    public Salgado(SalgadoDto salgadoDto){
        this.categoria = salgadoDto.categoria();
        this.price = salgadoDto.price();
        this.name = salgadoDto.name();
        this.description = salgadoDto.description();
        this.image = salgadoDto.image();
        this.inOffer = false;
        this.priceInOffer = salgadoDto.price();
        this.disponibilidade = salgadoDto.disponibilidade();
        this.acompanhamentos = salgadoDto.acompanhamentos();
    }
}
