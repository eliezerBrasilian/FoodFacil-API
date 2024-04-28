package com.br.foodfacil.ff.services;

import com.br.foodfacil.ff.dtos.*;
import com.br.foodfacil.ff.models.Pedido;
import com.br.foodfacil.ff.records.Address;
import com.br.foodfacil.ff.repositories.CupomRepository;
import com.br.foodfacil.ff.repositories.PedidoRepository;
import com.br.foodfacil.ff.repositories.UserRepository;
import com.br.foodfacil.ff.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    PedidoRepository pedidoRepository;

    @Autowired
    PagamentoService pagamentoService;


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

    public ResponseEntity<Object> updateAddress(Address address, String userId) {
        var optionalUser = userRepository.findById(userId);

        try {
            if(optionalUser.isEmpty()){
                var data = Map.of("message", "usuario não existe");
                return ResponseEntity.ok().body(data);
            }
           else {
                System.out.println("usuario existe");
                var user = optionalUser.get();
                user.setAddress(address);

                userRepository.save(user);
                var data = Map.of("message", "endereço atualizado");
                return ResponseEntity.ok().body(data);
            }
        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            System.out.println(data);
            return ResponseEntity.badRequest().body(data);
        }
    }

    public ResponseEntity<Object> addCupom(UserCupomDto userCupom) {
        var optionalUser = userRepository.findById(userCupom.userId());
        var optionalCupom = cupomRepository.findById(userCupom.cupom().id());

        try {
            if (optionalUser.isPresent() && optionalCupom.isEmpty()) {
                var data = Map.of("message", "cupom não existe");
                return ResponseEntity.badRequest().body(data);

            } else if (optionalUser.isEmpty() && optionalCupom.isPresent()) {
                var data = Map.of("message", "usuario não existe");
                return ResponseEntity.badRequest().body(data);
            }

            var cupomExpirado = AppUtils.verificaExpiracao(optionalCupom.get().getExpirationDate());
            var cupomRecebido = userCupom.cupom();

             if(cupomExpirado){
                var data = Map.of("message", "cupom está expirado");
                return ResponseEntity.badRequest().body(data);
            }

            var user = optionalUser.get();
            var cupomsExistentes = user.getCupoms();

            if (cupomsExistentes == null) {
                cupomsExistentes = new ArrayList<>();
            }
            else{
                for (SimpleCupomDto cupom_: cupomsExistentes){
                    if(Objects.equals(cupom_.id(), cupomRecebido.id())){
                        var data = Map.of("message", "cupom já está adicionado");
                        System.out.println(data);
                        return ResponseEntity.badRequest().body(data);
                    }
                }
            }

            var newCupom = new SimpleCupomDto(cupomRecebido.id(),
                    cupomRecebido.resgatado(),cupomRecebido.used());

            cupomsExistentes.add(newCupom);
            user.setCupoms(cupomsExistentes);
            userRepository.save(user);

            var data = Map.of("message", "cupom adicionado a conta",
                    "idcupom", cupomRecebido.id(),
                    "userUid", userCupom.userId());

            System.out.println("cupom adicionado");

            return ResponseEntity.ok().body(data);

        } catch (Exception e) {
           /* var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());*/
            System.out.println("mesnsagem de erro");
            System.out.println(e.getMessage());
            System.out.println("causa");
            //System.out.println(e.getCause());
            return ResponseEntity.badRequest().body("data");
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
            SimpleCupomDto newCupom;
            SimpleCupomDto cupomFounded = null;

            for (SimpleCupomDto item : cupomsList) {
                if (Objects.equals(item.id(), cupomToUpdateDto.cupomId())) {
                    System.out.println("encontrou");
                    cupomFounded = item;
                }
            }
            var index = cupomsList.indexOf(cupomFounded);
            cupomsList.set(index,
                    new SimpleCupomDto(cupomFounded.id(), cupomFounded.resgatado(), true));

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

    public ResponseEntity<Object> registraPedido(PedidoRequestDto pedidoRequestDto){
        try{
            var pedido = pedidoRepository.save(new Pedido(pedidoRequestDto));

          /*  var userData = new UserData(pedidoRequestDto.userId(), pedidoRequestDto.userEmail(),"isisiss",


                    )

            var pagamentoBody = new PagamentoBody();
            var qrcode = pagamentoService.geraPix()
            var data = Map.of("message","pedido registrado",
                    "id",pedido.getId());*/

          ;;  System.out.println(pedidoRequestDto);

            /*try{
                pagamentoService.geraPix();
            }
*/
            return ResponseEntity.ok().body("data");
        }catch (Exception e){
            throw new RuntimeException("erro ao salvar pedido devido a uma excessao: "+e.getMessage());
        }
    }

    public ResponseEntity<Object> getPedidos(String userId){
        var optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty() ){
            return ResponseEntity.badRequest().body(Map.of("message","usuário não existe"));
        }

        return ResponseEntity.ok().body(Map.of("message","sucesso",
                "lista",pedidoRepository.findByUserId(userId)));
    }
}
