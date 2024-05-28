package com.br.foodfacil.services;

import com.br.foodfacil.dtos.SalgadoRequestDto;
import com.br.foodfacil.dtos.SalgadoRequestEditDto;
import com.br.foodfacil.enums.Item;
import com.br.foodfacil.enums.MensagemRetorno;
import com.br.foodfacil.models.Salgado;
import com.br.foodfacil.repositories.SalgadoRepository;
import com.br.foodfacil.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface SalgadoService {

    ResponseEntity<Object> deleteAll();

    ResponseEntity<Object> registrar(SalgadoRequestDto salgadoDto);

    ResponseEntity<Object> excluiSalgado(String id);

    ResponseEntity<Object> editaSalgado(SalgadoRequestEditDto salgadoRequestEditDto, String id);

    ResponseEntity<Object> salgadosList();
}
