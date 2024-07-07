package com.br.foodfacil.controllers;

import com.br.foodfacil.dtos.AcompanhamentoRequestDto;
import com.br.foodfacil.dtos.AcompanhamentoRequestEditDto;
import com.br.foodfacil.services.impl.AcompanhamentoServiceImpl;
import com.br.foodfacil.utils.AppUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = {"http://localhost:5173", "https://food-facil-painel-admin-8mcqkbvbs-eliezerbrasilians-projects.vercel.app", "https://food-facil-painel-admin.vercel.app", "https://foodfacil-website.vercel.app"})
@RestController
@RequestMapping(AppUtils.baseUrl + "/acompanhamento")
public class AcompanhamentoController {
    @Autowired
    AcompanhamentoServiceImpl acompanhamentoServiceImpl;

    @PostMapping
    ResponseEntity<Object> registerAdicional(@RequestBody AcompanhamentoRequestDto acompanhamentoRequestDto) {
        return acompanhamentoServiceImpl.register(acompanhamentoRequestDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<Object> editaSalgado(@Valid @PathVariable String id, @RequestBody AcompanhamentoRequestEditDto acompanhamentoRequestEditDto) {

        return acompanhamentoServiceImpl.edita(acompanhamentoRequestEditDto, id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> excluiSalgado(@PathVariable String id) {

        return acompanhamentoServiceImpl.exclui(id);
    }

    @GetMapping
    ResponseEntity<Object> getAll() {
        return acompanhamentoServiceImpl.getAll();
    }
}
