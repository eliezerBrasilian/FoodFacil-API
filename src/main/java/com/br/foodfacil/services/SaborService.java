package com.br.foodfacil.services;

import com.br.foodfacil.dtos.SaborRequestDto;
import com.br.foodfacil.dtos.SaborRequestEditDto;
import com.br.foodfacil.enums.Item;
import com.br.foodfacil.enums.MensagemRetorno;
import com.br.foodfacil.models.Sabor;
import com.br.foodfacil.repositories.SaborRepository;
import com.br.foodfacil.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface SaborService {

    ResponseEntity<Object> getAll();

    @Transactional
    ResponseEntity<Object> registrar(SaborRequestDto saborRequestDto);

    ResponseEntity<Object> excluir(String id);

    ResponseEntity<Object> editar(SaborRequestEditDto saborRequestEditDto, String id);
}
