package com.br.foodfacil.controllers;

import com.br.foodfacil.dtos.SaborRequestDto;
import com.br.foodfacil.dtos.SaborRequestEditDto;
import com.br.foodfacil.services.impl.SaborServiceImpl;
import com.br.foodfacil.utils.AppUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://food-facil-painel-admin-8mcqkbvbs-eliezerbrasilians-projects.vercel.app",
        "https://food-facil-painel-admin.vercel.app",
        "https://foodfacil-website.vercel.app"
})

@RestController
@RequestMapping(AppUtils.baseUrl + "/sabor")

public class SaborController {
    @Autowired
    SaborServiceImpl saborServiceImpl;

    @PostMapping
    ResponseEntity<Object> registrar(@Valid @RequestBody SaborRequestDto saborRequestDto){
        return saborServiceImpl.registrar(saborRequestDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> editar(
            @Valid
            @PathVariable String id,
            @RequestBody SaborRequestEditDto saborRequestEditDto){

        return saborServiceImpl.editar(saborRequestEditDto, id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> excluiSalgado(
            @PathVariable String id){

        return saborServiceImpl.excluir(id);
    }

    @GetMapping
    ResponseEntity<Object> getAll(){
        return saborServiceImpl.getAll();
    }
}
