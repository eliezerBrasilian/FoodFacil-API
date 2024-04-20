package com.br.foodfacil.ff.repositories;

import com.br.foodfacil.ff.models.Adicional;
import com.br.foodfacil.ff.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface AdicionalRepository extends MongoRepository<Adicional, String> {
    //Optional<User> findByEmail(String email);
}
