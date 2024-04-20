package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.SalgadoDto;
import com.br.foodfacil.ff.enums.Categoria;
import com.br.foodfacil.ff.services.SalgadoService;
import com.br.foodfacil.ff.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppUtils.baseUrl + "/salgado")
public class SalgadoController {

    @Autowired
    SalgadoService salgadoService;

    @PostMapping
    ResponseEntity<Object> registerSalgado(
            @RequestBody SalgadoDto salgadoDto){

        return salgadoService.register(salgadoDto);
    }

    @GetMapping()
    ResponseEntity<Object> getAll(){
        return salgadoService.salgadosList();
    }

    @GetMapping("/in-offer")
    ResponseEntity<Object> getAllInOffer(){
        return salgadoService.salgadosInOfferList();
    }

    @GetMapping("/category/{category}")
    ResponseEntity<Object> getAllInOffer(@PathVariable Categoria category){
        return salgadoService.salgadosByCategoryList(category);
    }
}
