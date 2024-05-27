package com.br.foodfacil.controllers;

import com.br.foodfacil.dtos.PedidoRequestEditDto;
import com.br.foodfacil.services.impl.PedidoServiceImpl;
import com.br.foodfacil.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:5173",
        "https://food-facil-painel-admin-8mcqkbvbs-eliezerbrasilians-projects.vercel.app",
        "https://food-facil-painel-admin.vercel.app", "https://foodfacil-website.vercel.app"})
@RestController
@RequestMapping(AppUtils.baseUrl + "/pedido")
public class PedidosController {
    @Autowired
    PedidoServiceImpl pedidoServiceImpl;

    @GetMapping
    ResponseEntity<Object> getAll(){

        System.out.println("pedidos");
        return pedidoServiceImpl.getAll();
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> edita(
            @PathVariable String id,
            @RequestBody PedidoRequestEditDto pedidoRequestEditDto){
        System.out.println("pedido recebido");
        System.out.println(pedidoRequestEditDto);
        return  pedidoServiceImpl.editaStatus(pedidoRequestEditDto, id);
    }

    @GetMapping("/{id}")
    ResponseEntity<Object> buscaPedido(
        @PathVariable String id
    ){
        return pedidoServiceImpl.buscaPedido(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> apaga(
            @PathVariable String id){
        System.out.println("id do pedido: " + id);
        return  pedidoServiceImpl.exclui(id);
    }

}
