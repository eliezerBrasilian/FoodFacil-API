package com.br.foodfacil.repositories;

import com.br.foodfacil.models.Online;
import com.br.foodfacil.models.Sabor;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OnlineRepository extends MongoRepository<Online, String> {
}
