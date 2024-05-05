package com.br.foodfacil.repositories;

import com.br.foodfacil.models.TokenDoDIspositivo;
import com.br.foodfacil.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenDoDispositivoRepository extends MongoRepository<TokenDoDIspositivo, String> {
    Optional<TokenDoDIspositivo> findByUserId(String email);
}
