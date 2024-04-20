package com.br.foodfacil.ff.repositories;

import com.br.foodfacil.ff.models.Adicional;
import com.br.foodfacil.ff.models.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PedidoRepository extends MongoRepository<Pedido, String> {
    List<Pedido> findByCompradorId(String compradorId);
}
