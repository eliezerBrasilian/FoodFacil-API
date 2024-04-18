package com.br.foodfacil.ff.dtos;

import java.util.Date;

public record CupomDto(
        String code,
        int porcentoDeDesconto,
        Date createdAt,
        Date expirationDate,
        boolean expired,
        String description,
        CupomCategory cupomCategory
        ) {
}
