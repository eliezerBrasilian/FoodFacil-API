package com.br.foodfacil.services;

import com.br.foodfacil.dtos.SalgadoRequestDto;
import com.br.foodfacil.dtos.SalgadoRequestEditDto;
import com.br.foodfacil.enums.Categoria;
import com.br.foodfacil.enums.Item;
import com.br.foodfacil.enums.MensagemRetorno;
import com.br.foodfacil.models.Salgado;
import com.br.foodfacil.repositories.SalgadoRepository;
import com.br.foodfacil.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Service
public class SalgadoService {
    @Autowired
    SalgadoRepository salgadoRepository;

    public
    ResponseEntity<Object> deletaTudo(){
        try{
             salgadoRepository.deleteAll();
            var data = Map.of("message", "delatados com sucessso");
            return ResponseEntity.ok().body(data);
        }
        catch (RuntimeException e){
            throw new RuntimeException("excessao ocorreu ao tentar deletar todos salgadsos");
        }
    }

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
            return new AppUtils().AppCustomJson(MensagemRetorno.ITEM_NAO_EXISTE, Item.SALGADO);
        }

        try{
            salgadoRepository.deleteById(id);
            return new AppUtils().AppCustomJson(MensagemRetorno.EXCLUIDO_COM_SUCESSO, Item.SALGADO);
        }catch (RuntimeException e){
            throw new RuntimeException(AppUtils.CustomMensagemExcessao(MensagemRetorno.FALHA_AO_DELETAR,e.getMessage()));
        }
    }

    public ResponseEntity<Object> editaSalgado(SalgadoRequestEditDto salgadoRequestEditDto, String id){

        var optionalSalgado = salgadoRepository.findById(id);

        if(optionalSalgado.isEmpty()){
            return new AppUtils().AppCustomJson(MensagemRetorno.ITEM_NAO_EXISTE, Item.SALGADO);
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

            salgadoRepository.save(salgadoEncontrado);
            return new AppUtils().AppCustomJson(MensagemRetorno.EDITADO_COM_SUCESSO, Item.SALGADO);
        }catch (RuntimeException e){
            throw new RuntimeException(AppUtils.CustomMensagemExcessao(MensagemRetorno.FALHA_AO_EDITAR,e.getMessage()));
        }
    }

    public ResponseEntity<Object> salgadosList(){
           var list = salgadoRepository.findAll();
           return ResponseEntity.ok().body(Map.of("lista", list));
    }
}
