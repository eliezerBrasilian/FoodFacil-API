package com.br.foodfacil.ff.dtos;

import java.util.Map;

public record NotificationDTO(String token, String title,
                              String body, String image,
                              Map<String,String> data) {
}
