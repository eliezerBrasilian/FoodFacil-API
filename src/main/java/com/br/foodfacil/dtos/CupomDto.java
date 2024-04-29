package com.br.foodfacil.dtos;

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
