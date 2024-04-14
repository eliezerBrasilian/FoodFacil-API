package com.br.foodfacil.ff.services;

import com.br.foodfacil.ff.dtos.AddressDto;
import com.br.foodfacil.ff.dtos.ProfilePhotoDto;
import com.br.foodfacil.ff.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<Object> updatePhoto(ProfilePhotoDto profilePhotoDto) {
        var optionalUser = userRepository.findById(profilePhotoDto.userUid());

        try{
            if (optionalUser.isPresent()) {
                System.out.println("usuario existe");
                var user = optionalUser.get();
                user.setProfilePicture(profilePhotoDto.newProfilePhoto());

                userRepository.save(user);
                var data = Map.of("message", "foto de perfil atualizada");
                return ResponseEntity.ok().body(data);
            } else {
                var data = Map.of("message", "usuario não existe");
                return ResponseEntity.ok().body(data);
            }
        }catch (Exception e){
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }
    }

    public ResponseEntity<Object> updateAddress(AddressDto addressDto) {
        var optionalUser = userRepository.findById(addressDto.userUid());

        try{
            if (optionalUser.isPresent()) {
                System.out.println("usuario existe");
                var user = optionalUser.get();
                user.setAddress(addressDto.address());

                userRepository.save(user);
                var data = Map.of("message", "endereço atualizado");
                return ResponseEntity.ok().body(data);
            } else {
                var data = Map.of("message", "usuario não existe");
                return ResponseEntity.ok().body(data);
            }
        }catch (Exception e){
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }
    }


}
