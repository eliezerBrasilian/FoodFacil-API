package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.CupomDto;
import com.br.foodfacil.ff.services.CupomService;
import com.br.foodfacil.ff.utils.AppUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppUtils.baseUrl + "/cupom")
public class CupomController {

    @Autowired
    CupomService cupomService;

    @PostMapping
    ResponseEntity<Object> registerCupom(@RequestBody CupomDto cupomDto){
        return cupomService.registerCupom(cupomDto);
    }

    @GetMapping("{cupomId}")
    ResponseEntity<Object> findCupom(@PathVariable String cupomId){
        return cupomService.findCupom(cupomId);
    }

    @GetMapping()
    ResponseEntity<Object> getAll(){
        return cupomService.getAll();
    }
}
