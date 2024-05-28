package com.br.foodfacil.services.impl;

import com.br.foodfacil.dtos.SaborRequestDto;
import com.br.foodfacil.models.Ingrediente;
import com.br.foodfacil.repositories.IngredienteRepository;
import com.br.foodfacil.services.IngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IngredienteServiceImpl implements IngredienteService {
    @Autowired
    IngredienteRepository ingredienteRepository;

    @Override
    public ResponseEntity<Object> registra(SaborRequestDto saborRequestDto){
        try{
            var ingrediente = ingredienteRepository.save(new Ingrediente(saborRequestDto));

            var data = Map.of("message","ingrediente registrado",
                    "id",ingrediente.getId());

            return ResponseEntity.ok().body(data);
        }catch (Exception e){
            var data = Map.of("message",e.getMessage(),"cause",e.getCause());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
        }
    }

    @Override
    public ResponseEntity<Object> getAll(){
        var ingredienteList = ingredienteRepository.findAll();

        var data = Map.of("message","sucesso",
                "lista",ingredienteList);

        return ResponseEntity.ok().body(data);
    }
}
