package com.br.foodfacil.controllers;

import com.br.foodfacil.dtos.SalgadoRequestDto;
import com.br.foodfacil.dtos.SalgadoRequestEditDto;
import com.br.foodfacil.services.SalgadoService;
import com.br.foodfacil.utils.AppUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(AppUtils.baseUrl + "/salgado")
public class SalgadoController {

    @Autowired
    SalgadoService salgadoService;

    @PostMapping
    ResponseEntity<Object> registerSalgado(
            @RequestBody SalgadoRequestDto salgadoRequestDto){

        return salgadoService.registrar(salgadoRequestDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> editaSalgado(
            @PathVariable String id,
            @RequestBody SalgadoRequestEditDto salgadoRequestEditDto){

        return salgadoService.editaSalgado(salgadoRequestEditDto, id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> excluiSalgado(
            @PathVariable String id){

        return salgadoService.excluiSalgado(id);
    }

    @GetMapping()
    ResponseEntity<Object> getAll(){
        return salgadoService.salgadosList();
    }

}
