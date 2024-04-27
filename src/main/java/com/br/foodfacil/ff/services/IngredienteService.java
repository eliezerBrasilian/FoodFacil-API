package com.br.foodfacil.ff.services;

import com.br.foodfacil.ff.dtos.SaborDto;
import com.br.foodfacil.ff.models.Ingrediente;
import com.br.foodfacil.ff.repositories.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IngredienteService {
    @Autowired
    IngredienteRepository ingredienteRepository;

    public ResponseEntity<Object> registraIngrediente(SaborDto saborDto){
        try{
            var ingrediente = ingredienteRepository.save(new Ingrediente(saborDto));

            var data = Map.of("message","ingrediente registrado",
                    "id",ingrediente.getId());

            return ResponseEntity.ok().body(data);
        }catch (Exception e){
            var data = Map.of("message",e.getMessage(),"cause",e.getCause());
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
        }
    }

    public ResponseEntity<Object> getAllIngredientes(){
        var ingredienteList = ingredienteRepository.findAll();

        var data = Map.of("message","sucesso",
                "lista",ingredienteList);

        return ResponseEntity.ok().body(data);
    }
}
