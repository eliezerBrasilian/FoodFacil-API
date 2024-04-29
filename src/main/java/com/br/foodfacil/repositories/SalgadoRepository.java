package com.br.foodfacil.repositories;

import com.br.foodfacil.dtos.SalgadoResponseDto;
import com.br.foodfacil.enums.Categoria;
import com.br.foodfacil.models.Salgado;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SalgadoRepository extends MongoRepository<Salgado, String> {
}
