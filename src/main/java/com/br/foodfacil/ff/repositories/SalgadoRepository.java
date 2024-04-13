package com.br.foodfacil.ff.repositories;

import com.br.foodfacil.ff.dtos.SalgadoResponseDto;
import com.br.foodfacil.ff.enums.Categoria;
import com.br.foodfacil.ff.models.Salgado;
import com.br.foodfacil.ff.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SalgadoRepository extends MongoRepository<Salgado, String> {
    List<SalgadoResponseDto> findByInOffer(boolean inOffer);
    List<SalgadoResponseDto> findByCategoria(Categoria categoria);
}
