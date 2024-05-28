package com.br.foodfacil.services;

import com.br.foodfacil.dtos.AcompanhamentoRequestDto;
import com.br.foodfacil.dtos.AcompanhamentoRequestEditDto;
import com.br.foodfacil.models.Acompanhamento;
import com.br.foodfacil.repositories.AcompanhamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface AcompanhamentoService {
    ResponseEntity<Object> getAll();

    @Transactional
     ResponseEntity<Object> register(AcompanhamentoRequestDto acompanhamentoRequestDto);

     ResponseEntity<Object> exclui(String id);

     ResponseEntity<Object> edita(AcompanhamentoRequestEditDto acompanhamentoRequestEditDto, String id);

}
