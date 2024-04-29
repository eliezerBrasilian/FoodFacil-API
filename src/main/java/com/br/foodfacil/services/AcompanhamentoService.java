package com.br.foodfacil.services;

import com.br.foodfacil.dtos.AcompanhamentoRequestDto;
import com.br.foodfacil.dtos.AcompanhamentoRequestEditDto;
import com.br.foodfacil.models.Acompanhamento;
import com.br.foodfacil.repositories.AcompanhamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AcompanhamentoService {
    @Autowired
    AcompanhamentoRepository acompanhamentoRepository;

    @Transactional
    public ResponseEntity<Object> registerAdicional(AcompanhamentoRequestDto acompanhamentoRequestDto){
        try{
            var adicional = acompanhamentoRepository.save(new Acompanhamento(acompanhamentoRequestDto));

            var data = Map.of("message","acompanhamento registrado",
                    "id",adicional.getId());

            return ResponseEntity.ok().body(data);
        }catch (RuntimeException e){
            throw new RuntimeException("erro ao salvar acompanhamento, devido a excessão: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> exclui(String id){
        var optionalSalgado = acompanhamentoRepository.findById(id);

        if(optionalSalgado.isEmpty()){
            var data = Map.of("message","acompanhamento nao existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
        }

        try{
            acompanhamentoRepository.deleteById(id);
            var data = Map.of("message","acompanhamento excluido com sucesso");

            return ResponseEntity.ok().body(data);
        }catch (RuntimeException e){
            throw new RuntimeException("falha ao deletar devido a uma excessao: "+e.getMessage());
        }
    }

    public ResponseEntity<Object> edita(AcompanhamentoRequestEditDto acompanhamentoRequestEditDto, String id){

        var optionalAcompanhamento = acompanhamentoRepository.findById(id);

        if(optionalAcompanhamento.isEmpty()){
            var data = Map.of("message","salgado nao existe");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
        }

        try {
            var encontrado = optionalAcompanhamento.get();
            encontrado.setNome(acompanhamentoRequestEditDto.nome());
            encontrado.setDescricao(acompanhamentoRequestEditDto.descricao());
            encontrado.setImagem(acompanhamentoRequestEditDto.imagem());
            encontrado.setPreco(acompanhamentoRequestEditDto.preco());
            encontrado.setDisponibilidade(acompanhamentoRequestEditDto.disponibilidade());

            var salgado = acompanhamentoRepository.save(encontrado);

            var data = Map.of("message", "acompanhamento atualizado com sucesso no banco de dados",
                    "id", salgado.getId());

            return ResponseEntity.ok().body(data);
        }catch (RuntimeException e){
            throw new RuntimeException("falha ao editar acompanhamento devido a uma excessao: "+e.getMessage());
        }
    }


    public ResponseEntity<Object> getAllAdicionais(){

        try {
            var list = acompanhamentoRepository.findAll();

            var data = Map.of("message", "todos acompanhamentos",
                    "lista", list);

            return ResponseEntity.ok().body(data);
        }catch (RuntimeException e){
            throw new RuntimeException("falha ao trazer acompanhamentos devido a uma excessao: "+e.getMessage());
        }
    }
}
