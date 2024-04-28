package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.*;
import com.br.foodfacil.ff.records.Address;
import com.br.foodfacil.ff.services.UserService;
import com.br.foodfacil.ff.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AppUtils.baseUrl + "/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("update-photo")
    ResponseEntity<Object> updatePhoto(@RequestBody ProfilePhotoDto profilePhotoDto){
        return userService.updatePhoto(profilePhotoDto);
    }

    @PostMapping("address/update/{userId}")
    ResponseEntity<Object> updateAddress(@RequestBody Address address,
                                         @PathVariable String userId){
        System.out.println("address");
        System.out.println(address);
        System.out.println(userId);
        return userService.updateAddress(address, userId);
    }

    @PostMapping("cupom/add")
    ResponseEntity<Object> addCupom(@RequestBody UserCupomDto userCupom) {
        System.out.println("cupom recebido");

        return userService.addCupom(userCupom);
    }

    @PostMapping("cupom/use")
    ResponseEntity<Object> useCupom(@RequestBody CupomToUpdateDto cupomToUpdateDto){

        return userService.usarCupom(cupomToUpdateDto);
    }

    @GetMapping("cupoms/{userId}")
    ResponseEntity<Object> getCupoms(@PathVariable String userId){
        return userService.getCupoms(userId);
    }

    @PostMapping("/pedido")
    ResponseEntity<Object> criaPedido(@RequestBody PedidoRequestDto pedidoRequestDto){
        System.out.println("-----------pedidoRequestDto");
        System.out.println(pedidoRequestDto);
        return userService.registraPedido(pedidoRequestDto);
    }

    @GetMapping("pedidos/{userId}")
    ResponseEntity<Object> getPedidos(@PathVariable String userId){
        return userService.getPedidos(userId);
    }

}
