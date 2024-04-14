package com.br.foodfacil.ff.models;

import com.br.foodfacil.ff.dtos.CupomDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    float porcentoDeDesconto;
    Date createdAt;
    Date expirationDate;
    boolean expired;
    boolean used;

    public Cupom(CupomDto cupomDto){
        this.code = cupomDto.code();
        this.porcentoDeDesconto = cupomDto.porcentoDeDesconto();
        this.createdAt = cupomDto.createdAt();
        this.expirationDate = cupomDto.expirationDate();
        this.used = cupomDto.used();

        // Obtém a data e hora atuais
        LocalDateTime now = LocalDateTime.now();

        // Converte a data de expiração do cupom (java.util.Date) para uma String no formato ISO 8601
        Date expirationDate = cupomDto.expirationDate();
        String expirationDateString = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(expirationDate);

        // Formata a data de expiração do cupom para LocalDateTime
        LocalDateTime expirationDateTime = LocalDateTime.parse(expirationDateString, DateTimeFormatter.ISO_DATE_TIME);

        // Verifica se a data de expiração é anterior à data atual
        boolean expired = expirationDateTime.isBefore(now);

        // Define o status de expiração no cupom
        this.expired = expired;
    }
}
