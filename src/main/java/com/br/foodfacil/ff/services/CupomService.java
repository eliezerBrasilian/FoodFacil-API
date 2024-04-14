package com.br.foodfacil.ff.services;

import com.br.foodfacil.ff.dtos.CupomDto;
import com.br.foodfacil.ff.dtos.UserCupom;
import com.br.foodfacil.ff.models.Cupom;
import com.br.foodfacil.ff.repositories.CupomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CupomService {

    @Autowired
    CupomRepository cupomRepository;

    public ResponseEntity<Object> registerCupom(CupomDto cupomDto) {
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

    public ResponseEntity<Object> findCupom(String cupomId) {
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
