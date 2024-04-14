package com.br.foodfacil.ff.repositories;

import com.br.foodfacil.ff.models.Adicional;
import com.br.foodfacil.ff.models.Cupom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CupomRepository extends MongoRepository<Cupom, String> {

}
