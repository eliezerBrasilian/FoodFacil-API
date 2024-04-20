package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.LoginAuthDTO;
import com.br.foodfacil.ff.dtos.RegisterDto;
import com.br.foodfacil.ff.services.AuthService;
import com.br.foodfacil.ff.utils.AppUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppUtils.baseUrl + "/auth")
public class AuthController {

    @Autowired
    AuthService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginAuthDTO authetinticationDto) {
        return authorizationService.login(authetinticationDto);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
        System.out.println(registerDto.toString());
        return authorizationService.register(registerDto);
    }

    @PostMapping("/google-login")
    public ResponseEntity<Object> loginWithGoogle(@RequestBody RegisterDto registerDto) {
        System.out.println(registerDto.toString());
        return authorizationService.loginWithGoogle(registerDto);
    }
}