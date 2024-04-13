package com.br.foodfacil.ff.services;

import com.br.foodfacil.ff.dtos.AdicionalDto;
import com.br.foodfacil.ff.models.Adicional;
import com.br.foodfacil.ff.repositories.AdicionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class AdicionalService {
    @Autowired
    AdicionalRepository adicionalRepository;

    public ResponseEntity<Object> registerAdicional(AdicionalDto adicionalDto){
        var adicional = adicionalRepository.save(new Adicional(adicionalDto));

        var data = Map.of("message","item adicional registrado",
                "id",adicional.getId());

        return ResponseEntity.ok().body(data);
    }

    public ResponseEntity<Object> getAllAdicionais(){
        var adicionaisList = adicionalRepository.findAll();

        var data = Map.of("message","item adicional registrado",
                "lista",adicionaisList);

        return ResponseEntity.ok().body(data);
    }
}
