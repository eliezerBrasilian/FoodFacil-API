package com.br.foodfacil.ff.repositories;

import com.br.foodfacil.ff.models.Adicional;
import com.br.foodfacil.ff.models.Ingrediente;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface IngredienteRepository extends MongoRepository<Ingrediente, String> {
    //Optional<User> findByEmail(String email);
}
