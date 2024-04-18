package com.br.foodfacil.ff.models;

import com.br.foodfacil.ff.dtos.CupomCategory;
import com.br.foodfacil.ff.dtos.CupomDto;
import com.br.foodfacil.ff.utils.AppUtils;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "cupoms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Cupom {
    @Id
    String id;
    String code;
    int porcentoDeDesconto;
    Date createdAt;
    Date expirationDate;
    boolean expired;
    String description;
    CupomCategory cupomCategory;

    public Cupom(CupomDto cupomDto){
        this.code = cupomDto.code();
        this.porcentoDeDesconto = cupomDto.porcentoDeDesconto();
        this.createdAt = cupomDto.createdAt();
        this.expirationDate = cupomDto.expirationDate();
        this.description = cupomDto.description();
        this.cupomCategory = cupomDto.cupomCategory();

        // Define o status de expiração no cupom
        this.expired = AppUtils.verificaExpiracao(cupomDto.expirationDate());
    }

}
