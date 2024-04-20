package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.IngredienteDto;
import com.br.foodfacil.ff.services.IngredienteService;
import com.br.foodfacil.ff.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppUtils.baseUrl + "/ingrediente")
public class IngredientesController {
    @Autowired
    IngredienteService ingredienteService;

    @PostMapping
    ResponseEntity<Object> cadastraIngrediente(@RequestBody IngredienteDto ingredienteDto){

        System.out.println(ingredienteDto);
        return ingredienteService.registraIngrediente(ingredienteDto);
    }

    @GetMapping
    ResponseEntity<Object> getAllIngredientes(){
        return ingredienteService.getAllIngredientes();
    }
}
