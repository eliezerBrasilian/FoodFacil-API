package com.br.foodfacil.ff.services;

import com.br.foodfacil.ff.dtos.SalgadoDto;
import com.br.foodfacil.ff.dtos.SalgadoResponseDto;
import com.br.foodfacil.ff.enums.Categoria;
import com.br.foodfacil.ff.models.Salgado;
import com.br.foodfacil.ff.repositories.SalgadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalgadoService {
    @Autowired
    SalgadoRepository salgadoRepository;

    public ResponseEntity<Object> register(SalgadoDto salgadoDto){

        var salgado = salgadoRepository.save(new Salgado(salgadoDto));

        var data = Map.of("message","salgado salvo com sucesso",
                "id",salgado.getId());

        return ResponseEntity.ok().body(data);
    }

    public ResponseEntity<Object> salgadosList(){
        var list = salgadoRepository.findAll();

        var data = Map.of("message","todos salgados",
                "lista",list);

        return ResponseEntity.ok().body(data);
    }

    public ResponseEntity<Object> salgadosInOfferList(){
        var list = salgadoRepository.findByInOffer(true);

        var data = Map.of("message","salgados em oferta",
                "lista",list);

       return ResponseEntity.ok().body(data);
    }

    public ResponseEntity<Object> salgadosByCategoryList(Categoria categoria){
        var list = salgadoRepository.findByCategoria(categoria);

        var data = Map.of("message","salgados na categoria: " + categoria,
                "lista",list);

        return ResponseEntity.ok().body(data);
    }
}
