package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.AddressDto;
import com.br.foodfacil.ff.dtos.ProfilePhotoDto;
import com.br.foodfacil.ff.services.UserService;
import com.br.foodfacil.ff.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppUtils.baseUrl + "/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/update-photo")
    ResponseEntity<Object> updatePhoto(@RequestBody ProfilePhotoDto profilePhotoDto){
        return userService.updatePhoto(profilePhotoDto);
    }

    @PostMapping("update-address")
    ResponseEntity<Object> updateAddress(@RequestBody AddressDto addressDto){
        return userService.updateAddress(addressDto);
    }
}
