package com.br.foodfacil.services;


import com.br.foodfacil.dtos.*;
import com.br.foodfacil.records.Address;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Object> updatePhoto(ProfilePhotoDto profilePhotoDto);

    ResponseEntity<Object> updateAddress(Address address, String userId);

    ResponseEntity<Object> addCupom(UserCupomDto userCupom);

    ResponseEntity<Object> usarCupom(CupomToUpdateDto cupomToUpdateDto);

    ResponseEntity<Object> getCupoms(String userId);

    ResponseEntity<Object> registraPedido(PedidoRequestDto pedidoRequestDto);

    ResponseEntity<Object> getPedidos(String userId);

    ResponseEntity<Object> salvaOuAtualizaToken(TokenDoDispositivoRequestDto tokenDoDispositivoRequestDto);
}

