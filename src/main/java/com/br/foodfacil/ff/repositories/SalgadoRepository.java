package com.br.foodfacil.ff.repositories;

import com.br.foodfacil.ff.dtos.SalgadoResponseDto;
import com.br.foodfacil.ff.enums.Categoria;
import com.br.foodfacil.ff.models.Salgado;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SalgadoRepository extends MongoRepository<Salgado, String> {
    List<SalgadoResponseDto> findByEmOferta(boolean inOffer);
    List<SalgadoResponseDto> findByCategoria(Categoria categoria);
}
