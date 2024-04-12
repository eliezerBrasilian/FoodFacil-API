package com.br.foodfacil.ff.repositories;

import com.br.foodfacil.ff.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserRepository extends MongoRepository<UserModel, Long> {
    UserDetails findByEmail(String email);
}
