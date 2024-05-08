package com.br.foodfacil.controllers;

import com.br.foodfacil.dtos.SaborDto;
import com.br.foodfacil.services.IngredienteService;
import com.br.foodfacil.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = {"http://localhost:5173",
        "https://food-facil-painel-admin-8mcqkbvbs-eliezerbrasilians-projects.vercel.app",
        "https://food-facil-painel-admin.vercel.app"})
@RestController
@RequestMapping(AppUtils.baseUrl + "/ingrediente")
public class IngredientesController {
    @Autowired
    IngredienteService ingredienteService;

    @PostMapping
    ResponseEntity<Object> cadastraIngrediente(@RequestBody SaborDto saborDto){

        System.out.println(saborDto);
        return ingredienteService.registraIngrediente(saborDto);
    }

    @GetMapping
    ResponseEntity<Object> getAllIngredientes(){
        return ingredienteService.getAllIngredientes();
    }
}
