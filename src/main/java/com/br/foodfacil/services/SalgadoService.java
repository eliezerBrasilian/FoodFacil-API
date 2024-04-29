package com.br.foodfacil.services;

import com.br.foodfacil.dtos.SalgadoRequestDto;
import com.br.foodfacil.dtos.SalgadoRequestEditDto;
import com.br.foodfacil.enums.Categoria;
import com.br.foodfacil.models.Salgado;
import com.br.foodfacil.repositories.SalgadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SalgadoService {
    @Autowired
    SalgadoRepository salgadoRepository;

    public ResponseEntity<Object> registrar(SalgadoRequestDto salgadoDto){

        try {
            var salgado = salgadoRepository.save(new Salgado(salgadoDto));

            var data = Map.of("message", "salgado salvo com sucesso",
                    "id", salgado.getId());

            return ResponseEntity.ok().body(data);
        }catch (RuntimeException e){
            throw new RuntimeException("falha ao salvar devido a uma excessao: "+e.getMessage());
        }
    }

    public ResponseEntity<Object> excluiSalgado(String id){
        var optionalSalgado = salgadoRepository.findById(id);

        if(optionalSalgado.isEmpty()){
            var data = Map.of("message","salgado nao existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
        }

        try{
            salgadoRepository.deleteById(id);
            var data = Map.of("message","salgado excluido com sucesso");

            return ResponseEntity.ok().body(data);
        }catch (RuntimeException e){
            throw new RuntimeException("falha ao deletar devido a uma excessao: "+e.getMessage());
        }
    }

    public ResponseEntity<Object> editaSalgado(SalgadoRequestEditDto salgadoRequestEditDto, String id){

        var optionalSalgado = salgadoRepository.findById(id);

        if(optionalSalgado.isEmpty()){
            var data = Map.of("message","salgado nao existe");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
        }

        try {
            var salgadoEncontrado = optionalSalgado.get();
            salgadoEncontrado.setNome(salgadoRequestEditDto.nome());
            salgadoEncontrado.setCategoria(salgadoRequestEditDto.categoria());
            salgadoEncontrado.setDescricao(salgadoRequestEditDto.descricao());
            salgadoEncontrado.setImagem(salgadoRequestEditDto.imagem());
            salgadoEncontrado.setImagemQuadrada(salgadoRequestEditDto.imagemQuadrada());
            salgadoEncontrado.setImagemRetangular(salgadoRequestEditDto.imagemRetangular());
            salgadoEncontrado.setEmOferta(salgadoRequestEditDto.emOferta());
            salgadoEncontrado.setPreco(salgadoRequestEditDto.preco());
            salgadoEncontrado.setPrecoEmOferta(salgadoRequestEditDto.precoEmOferta());
            salgadoEncontrado.setSabores(salgadoRequestEditDto.sabores());
            salgadoEncontrado.setDisponibilidade(salgadoRequestEditDto.disponibilidade());

            var salgado = salgadoRepository.save(salgadoEncontrado);

            var data = Map.of("message", "salgado atualizado com sucesso no banco de dados",
                    "id", salgado.getId());

            return ResponseEntity.ok().body(data);
        }catch (RuntimeException e){
            throw new RuntimeException("falha ao editar salgado devido a uma excessao: "+e.getMessage());
        }
    }

    public ResponseEntity<Object> salgadosList(){

       try {
           var list = salgadoRepository.findAll();

           var data = Map.of("message", "todos salgados",
                   "lista", list);

           return ResponseEntity.ok().body(data);
       }catch (RuntimeException e){
           throw new RuntimeException("falha ao trazer salgados devido a uma excessao: "+e.getMessage());
       }
    }
}
