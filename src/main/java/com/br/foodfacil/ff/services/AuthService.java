package com.br.foodfacil.ff.services;
import com.br.foodfacil.ff.dtos.AuthDTO;
import com.br.foodfacil.ff.dtos.LoginResponseDto;
import com.br.foodfacil.ff.dtos.RegisterDto;
import com.br.foodfacil.ff.dtos.RegisterGoogleDto;
import com.br.foodfacil.ff.models.UserModel;
import com.br.foodfacil.ff.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.util.Map;

@Slf4j
@Service
public class AuthService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthDTO data) {
        authenticationManager = context.getBean(AuthenticationManager.class);

        try{
            System.out.println("em login");
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((UserModel) auth.getPrincipal());
            return ResponseEntity.ok(token);
        }catch (RuntimeException e){
            e.printStackTrace();
            System.out.println("tentativa de login fracassou");
            return ResponseEntity.badRequest().body("tentativa de login fracassou");
        }

    }

    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
        System.out.println("em register");
        System.out.println(registerDto);

        if (this.userRepository.findByEmail(registerDto.email()) != null){
            return ResponseEntity.badRequest().body("este email já está em uso");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        var newUser = new UserModel(registerDto.email(), encryptedPassword, registerDto.role());
        newUser.setCreatedAt(new Date(System.currentTimeMillis()));
        this.userRepository.save(newUser);

        var token = tokenService.generateToken(newUser);

        // Return token along with success response
        return ResponseEntity.ok(Map.of("message", "User registered successfully", "token", token));
    }

    //logando depois de clicar em sigin com google

    public ResponseEntity<Object> loginWithGoogle(RegisterDto registerDto){
        System.out.println("em register");
        System.out.println(registerDto);
        authenticationManager = context.getBean(AuthenticationManager.class);

        if (this.userRepository.findByEmail(registerDto.email()) != null){
            try{
                System.out.println("em loginWithGoogle");
                var usernamePassword = new UsernamePasswordAuthenticationToken(registerDto.email(), registerDto.password());
                var auth = this.authenticationManager.authenticate(usernamePassword);

                var token = tokenService.generateToken((UserModel) auth.getPrincipal());
                return ResponseEntity.ok(Map.of("message", "usuario logado com sucesso", "token", token));
            }catch (Exception e){

                e.printStackTrace();
                System.out.println(e.getMessage());
                if(e.getMessage() == "Bad credentials"){
                    return ResponseEntity.badRequest().body("senha incorreta");
                }
                return ResponseEntity.badRequest().body("tentativa de login fracassou");
            }
        }else{
            String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

            var newUser = new UserModel(registerDto.email(), encryptedPassword, registerDto.role());
            newUser.setCreatedAt(new Date(System.currentTimeMillis()));
            this.userRepository.save(newUser);
            var token = tokenService.generateToken(newUser);

            // Return token along with success response
            return ResponseEntity.ok(Map.of("message", "usuario registrado com sucesso", "token", token));
        }
    }

}