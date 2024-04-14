package com.br.foodfacil.ff.services;

import com.br.foodfacil.ff.dtos.AddressDto;
import com.br.foodfacil.ff.dtos.CupomToUpdateDto;
import com.br.foodfacil.ff.dtos.ProfilePhotoDto;
import com.br.foodfacil.ff.dtos.UserCupom;
import com.br.foodfacil.ff.models.Cupom;
import com.br.foodfacil.ff.repositories.CupomRepository;
import com.br.foodfacil.ff.repositories.UserRepository;
import com.br.foodfacil.ff.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CupomRepository cupomRepository;
    @Autowired
    CupomService cupomService;

    public ResponseEntity<Object> updatePhoto(ProfilePhotoDto profilePhotoDto) {
        var optionalUser = userRepository.findById(profilePhotoDto.userUid());

        try {
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
        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }
    }

    public ResponseEntity<Object> updateAddress(AddressDto addressDto) {
        var optionalUser = userRepository.findById(addressDto.userUid());

        try {
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
        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }
    }

    public ResponseEntity<Object> addCupom(UserCupom userCupom) {
        var optionalUser = userRepository.findById(userCupom.userUid());
        var optionalCupom = cupomRepository.findById(userCupom.cupom().getId());

        var cupom = userCupom.cupom();
        var cupomExpirado = AppUtils.verificaExpiracao(cupom.getExpirationDate());

        try {
            if (optionalUser.isPresent() && optionalCupom.isEmpty()) {
                var data = Map.of("message", "cupom não existe");
                return ResponseEntity.badRequest().body(data);

            } else if (optionalUser.isEmpty() && optionalCupom.isPresent()) {
                var data = Map.of("message", "usuario não existe");
                return ResponseEntity.badRequest().body(data);
            }
            else if(cupomExpirado){
                var data = Map.of("message", "cupom está expirado");
                return ResponseEntity.badRequest().body(data);
            }

            var user = optionalUser.get();
            var cupomsExistentes = user.getCupoms();

            for (Cupom cupom_: cupomsExistentes){
                if(Objects.equals(cupom_.getId(), cupom.getId())){
                    var data = Map.of("message", "cupom já está adicionado");
                    return ResponseEntity.badRequest().body(data);
                }
            }

            if (cupomsExistentes == null || cupomsExistentes.isEmpty()) {
                cupomsExistentes = new ArrayList<>();
            }

            cupom.setUsed(false);

            cupomsExistentes.add(cupom);
            user.setCupoms(cupomsExistentes);
            userRepository.save(user);

            var data = Map.of("message", "cupom adicionado a conta",
                    "idcupom", userCupom.cupom().getId(),
                    "userUid", userCupom.userUid());

            return ResponseEntity.ok().body(data);

        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }
    }

    public ResponseEntity<Object> usarCupom(CupomToUpdateDto cupomToUpdateDto) {
        var optionalUser = userRepository.findById(cupomToUpdateDto.userId());
        var optionalCupom = cupomRepository.findById(cupomToUpdateDto.cupomId());

        System.out.println("userId: " + cupomToUpdateDto.userId());
        System.out.println("cupomId: " + cupomToUpdateDto.cupomId());

        try {
            if (optionalUser.isPresent() && optionalCupom.isEmpty()) {
                return ResponseEntity.ok().body(Map.of("message", "cupom não existe"));
            } else if (optionalCupom.isPresent() && optionalUser.isEmpty()) {
                return ResponseEntity.ok().body(Map.of("message", "usuario não existe"));
            }

            var user = optionalUser.get();
            var cupomsList = user.getCupoms();

            for (Cupom item : cupomsList) {
                if (Objects.equals(item.getId(), cupomToUpdateDto.cupomId())) {
                    System.out.println("encontrou");
                    item.setUsed(true);
                }
            }

            user.setCupoms(cupomsList);
            userRepository.save(user);

            var data = Map.of("message", "cupom atualizado",
                    "cupoms", cupomsList);

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage(), "causa", e.getCause()));
        }
    }

    public ResponseEntity<Object> getCupoms(String userId) {
        var userOptional = userRepository.findById(userId);

        try {
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "usuario nao existe"));
            }

            var cupomsList = userOptional.get().getCupoms();

            return ResponseEntity.ok().body(Map.of(
                    "cupoms", cupomsList));

        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }

    }
}
