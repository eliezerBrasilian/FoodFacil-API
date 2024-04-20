package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.PedidoDto;
import com.br.foodfacil.ff.utils.AppUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppUtils.baseUrl + "/pedido")
public class PedidosController {

    @PostMapping
    ResponseEntity<Object> getPedidos(@RequestBody PedidoDto pedidoDto){

        System.out.println(pedidoDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pedidoDto);
    }
}
