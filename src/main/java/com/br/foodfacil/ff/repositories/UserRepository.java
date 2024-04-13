package com.br.foodfacil.ff.repositories;

import com.br.foodfacil.ff.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


public interface UserRepository extends MongoRepository<UserModel, Long> {
    @Query("{ 'email' : ?0 }")
    Optional<UserModel> findByEmail(String email);
}
