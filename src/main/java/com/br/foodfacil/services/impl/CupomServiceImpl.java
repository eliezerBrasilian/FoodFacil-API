package com.br.foodfacil.services.impl;

import com.br.foodfacil.dtos.CupomDto;
import com.br.foodfacil.models.Cupom;
import com.br.foodfacil.repositories.CupomRepository;
import com.br.foodfacil.services.CupomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CupomServiceImpl implements CupomService {

    @Autowired
    CupomRepository cupomRepository;

    @Override
    public ResponseEntity<Object> registra(CupomDto cupomDto) {
        try {
            var cupom = cupomRepository.save(new Cupom(cupomDto));
            var data = Map.of("message", "cupom adicionado",
                    "id", cupom.getId());
            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }
    }

    @Override
    public ResponseEntity<Object> buscaCupom(String cupomId) {
        var cupomOptional = cupomRepository.findById(cupomId);

        try {
            if (cupomOptional.isPresent()) {
                var cupom = cupomOptional.get();
                return ResponseEntity.ok().body(Map.of("cupom", cupom));
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "cupom nao existe"));
            }

        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }
    }

    public ResponseEntity<Object> getAll() {
        try {
            var cupomList = cupomRepository.findAll();
            return ResponseEntity.ok().body(Map.of("cupoms", cupomList));

        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }
    }
}
