package com.br.foodfacil.ff.dtos;

import java.util.Date;

public record CupomDto(
        String code,
        float porcentoDeDesconto,
        Date createdAt,
        Date expirationDate,
        boolean expired,
        boolean used
        ) {
}
