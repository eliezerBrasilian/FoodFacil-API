package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.AdicionalDto;
import com.br.foodfacil.ff.services.AdicionalService;
import com.br.foodfacil.ff.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppUtils.baseUrl + "/adicional")
public class AdicionalController {
    @Autowired
    AdicionalService adicionalService;

    @PostMapping
    ResponseEntity<Object> registerAdicional(@RequestBody AdicionalDto adicionalDto){
        return adicionalService.registerAdicional(adicionalDto);
    }

    @GetMapping
    ResponseEntity<Object> getAllAdicionais(){
        return adicionalService.getAllAdicionais();
    }
}
