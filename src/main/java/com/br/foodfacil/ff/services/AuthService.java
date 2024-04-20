package com.br.foodfacil.ff.services;
import com.br.foodfacil.ff.dtos.LoginAuthDTO;
import com.br.foodfacil.ff.dtos.RegisterDto;
import com.br.foodfacil.ff.models.User;
import com.br.foodfacil.ff.repositories.UserRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@Getter
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
        var userModelOptional = userRepository.findByEmail(email);
        if (userModelOptional.isPresent()) {
            var userModel = userModelOptional.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userModel.getEmail())
                    .password(userModel.getPassword())
                    .authorities(userModel.getAuthorities())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    public ResponseEntity<Object> login(LoginAuthDTO data) {
        authenticationManager = context.getBean(AuthenticationManager.class);
        var userOptioal = this.userRepository.findByEmail(data.email());

        if (userOptioal.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body((Map.of("message", "email não encontrado")));
        }

        try{
            System.out.println("em login");
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var user = userOptioal.get();
            System.out.println(Map.of("user",user.getName()));

            var token = tokenService.generateToken(user);
            //var token = tokenService.generateToken((User) auth.getPrincipal());

            return ResponseEntity.ok().body(
                    new HashMap<>()
                    {{
                        put("message", "logado com sucesso");
                        put("token", token);
                        put("userId", user.getId());
                        put("profilePicture", user.getProfilePicture() == null? "" : user.getProfilePicture());
                        put("name", user.getName());
                    }}
            );
        }catch (RuntimeException e){
            System.out.println("tentativa de login fracassou");
            System.out.println(Map.of(
                    "message",e.getMessage()));

            return ResponseEntity.badRequest().body(Map.of(
                    "message",e.getMessage()));
        }
    }

    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
        System.out.println("em register");
        System.out.println(registerDto);

        if (this.userRepository.findByEmail(registerDto.email()).isPresent()){
            return ResponseEntity.badRequest().body(Map.of("message", "este email já está em uso"));
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        try{
            var newUser = new User(registerDto);
            newUser.setCreatedAt(new Date(System.currentTimeMillis()));
            newUser.setCupoms(Collections.emptyList());
            newUser.setAmountOfCouponsCollected(0);
            newUser.setMoneySpentTotal((double) 0);
            newUser.setAmountOfCouponsUsed(0);
            newUser.setAmountOfItemsBoughtTotal(0);
            newUser.setPassword(encryptedPassword);

            var savedUser = this.userRepository.save(newUser);

            var token = tokenService.generateToken(newUser);

            // Return token along with success response
            return ResponseEntity.ok(Map.of("message", "User registered successfully",
                    "token", token,"userId", savedUser.getId()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(
                    Map.of("message", e.getMessage(), "cause", e.getCause())
            );
        }

    }

    public ResponseEntity<Object> loginWithGoogle(RegisterDto registerDto){
        System.out.println("em register");
        System.out.println(registerDto);
        authenticationManager = context.getBean(AuthenticationManager.class);

        var userFoundedOptional = (this.userRepository.findByEmail(registerDto.email()));

        if(userFoundedOptional.isPresent()){
            var userFounded = userFoundedOptional.get();

            System.out.println("userFounded");
            System.out.println(userFounded.getName());

            var data = new HashMap<>(Map.of("message", "usuario logado com sucesso",
                    "userUid", userFounded.getId(),
                    "name", userFounded.getName(),
                    "address",userFounded.getAddress()
            ));

            if(userFounded.getProfilePicture() != null)data.put("profilePicture", userFounded.getProfilePicture());

            try {
                System.out.println("em loginWithGoogle");
                var usernamePassword = new UsernamePasswordAuthenticationToken(registerDto.email(), registerDto.password());

                var user = userFoundedOptional.get();

                var token = tokenService.generateToken(user);
                data.put("token", token);
                return ResponseEntity.ok(data);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                if (Objects.equals(e.getMessage(), "Bad credentials")) {
                    System.out.println("senha incorreta");
                    return ResponseEntity.badRequest().body("senha incorreta");
                }
                return ResponseEntity.badRequest().body("tentativa de login fracassou");
            }
        }

        else{
            //String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

            var newUser = new User(registerDto);
            newUser.setCreatedAt(new Date(System.currentTimeMillis()));
            try{
                var userSalvo = this.userRepository.save(newUser);
                System.out.println("userSalvo: ");
                System.out.println(userSalvo);
                var token = tokenService.generateToken(newUser);

                var data = new HashMap<>(
                        Map.of("message", "usuario criado com sucesso",
                        "token",token,
                        "userUid", userSalvo.getId(),
                        "name", userSalvo.getName()
                ));

                if(userSalvo.getProfilePicture() != null)data.put("profilePicture", userSalvo.getProfilePicture());

                // Return token along with success response
                return ResponseEntity.ok(data);
            }catch (Exception e){
                System.out.println("falha ao salvar o usario");
                e.printStackTrace();
                return ResponseEntity.badRequest().body("falha ao salvar o usario");
            }
        }
    }

}