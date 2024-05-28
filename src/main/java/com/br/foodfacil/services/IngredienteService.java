package com.br.foodfacil.services;

import com.br.foodfacil.dtos.NotificacaoRequestDTO;
import com.br.foodfacil.dtos.NotificationDTO;
import com.br.foodfacil.dtos.SaborRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface IngredienteService {

    ResponseEntity<Object> registra(SaborRequestDto saborRequestDto);

    ResponseEntity<Object> getAll();

}
