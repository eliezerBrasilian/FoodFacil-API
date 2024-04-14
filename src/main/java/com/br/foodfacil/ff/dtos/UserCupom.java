package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.models.Cupom;

public record UserCupom(
        String userUid,
        Cupom cupom
) {
}
