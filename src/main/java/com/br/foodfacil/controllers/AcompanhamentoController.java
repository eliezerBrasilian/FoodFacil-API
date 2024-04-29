package com.br.foodfacil.controllers;

import com.br.foodfacil.dtos.AcompanhamentoRequestDto;
import com.br.foodfacil.dtos.AcompanhamentoRequestEditDto;
import com.br.foodfacil.services.AcompanhamentoService;
import com.br.foodfacil.utils.AppUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(AppUtils.baseUrl + "/acompanhamento")
public class AcompanhamentoController {
    @Autowired
    AcompanhamentoService acompanhamentoService;

    @PostMapping
    ResponseEntity<Object> registerAdicional(@RequestBody AcompanhamentoRequestDto acompanhamentoRequestDto){
        return acompanhamentoService.registerAdicional(acompanhamentoRequestDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> editaSalgado(
            @Valid
            @PathVariable String id,
            @RequestBody AcompanhamentoRequestEditDto acompanhamentoRequestEditDto){

        return acompanhamentoService.edita(acompanhamentoRequestEditDto, id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> excluiSalgado(
            @PathVariable String id){

        return acompanhamentoService.exclui(id);
    }

    @GetMapping
    ResponseEntity<Object> getAll(){
        return acompanhamentoService.getAllAdicionais();
    }
}
