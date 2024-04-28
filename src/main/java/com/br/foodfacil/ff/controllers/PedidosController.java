package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.PedidoRequestDto;
import com.br.foodfacil.ff.utils.AppUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(AppUtils.baseUrl + "/pedido")
public class PedidosController {

    @PostMapping
    ResponseEntity<Object> registraPedido(@RequestBody PedidoRequestDto pedidoRequestDto){

        System.out.println(pedidoRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Map.of("message","sucesso total"));
    }
}
