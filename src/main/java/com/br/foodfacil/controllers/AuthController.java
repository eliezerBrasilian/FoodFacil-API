package com.br.foodfacil.controllers;

import com.br.foodfacil.dtos.LoginAuthDTO;
import com.br.foodfacil.dtos.AuthRequestDto;
import com.br.foodfacil.services.AuthService;
import com.br.foodfacil.utils.AppUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(AppUtils.baseUrl + "/auth")
public class AuthController {

    @Autowired
    AuthService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginAuthDTO authetinticationDto) {
        System.out.println(authetinticationDto);
        return authorizationService.login(authetinticationDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> register(@RequestBody AuthRequestDto authRequestDto) {
        System.out.println(authRequestDto.toString());
        return authorizationService.register(authRequestDto);
    }

    @PostMapping("/google-login")
    public ResponseEntity<Object> loginWithGoogle(@RequestBody AuthRequestDto authRequestDto) {
        System.out.println(authRequestDto.toString());
        return authorizationService.loginWithGoogle(authRequestDto);
    }
}