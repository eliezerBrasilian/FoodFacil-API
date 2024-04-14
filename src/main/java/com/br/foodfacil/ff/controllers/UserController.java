package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.AddressDto;
import com.br.foodfacil.ff.dtos.CupomToUpdateDto;
import com.br.foodfacil.ff.dtos.ProfilePhotoDto;
import com.br.foodfacil.ff.dtos.UserCupom;
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

    @PostMapping("update-address")
    ResponseEntity<Object> updateAddress(@RequestBody AddressDto addressDto){
        return userService.updateAddress(addressDto);
    }

    @PostMapping("add-cupom")
    ResponseEntity<Object> addCupom(@RequestBody UserCupom userCupom){
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
}
