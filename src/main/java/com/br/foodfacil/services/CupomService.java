package com.br.foodfacil.services;

import com.br.foodfacil.dtos.CupomDto;
import com.br.foodfacil.models.Cupom;
import com.br.foodfacil.repositories.CupomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface CupomService {
    ResponseEntity<Object> getAll();

    ResponseEntity<Object> registra(CupomDto cupomDto);

    ResponseEntity<Object> buscaCupom(String cupomId);


}
