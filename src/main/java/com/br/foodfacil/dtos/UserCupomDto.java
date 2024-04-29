package com.br.foodfacil.dtos;

public record UserCupomDto(
        String userId,
        SimpleCupomDto cupom
) {
}
