package com.br.foodfacil.ff.services;

import com.br.foodfacil.ff.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PedidosService {
    @Autowired
    PedidoRepository pedidoRepository;


    public ResponseEntity<Object> getAllPedidos(){
        var ingredienteList = pedidoRepository.findAll();

        var data = Map.of("message","sucesso",
                "lista",ingredienteList);

        return ResponseEntity.ok().body(data);
    }
}
