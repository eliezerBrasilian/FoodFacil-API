package com.br.foodfacil.services;

import com.br.foodfacil.dtos.SaborRequestDto;
import com.br.foodfacil.dtos.SaborRequestEditDto;
import com.br.foodfacil.enums.Item;
import com.br.foodfacil.enums.MensagemRetorno;
import com.br.foodfacil.models.Sabor;
import com.br.foodfacil.repositories.SaborRepository;
import com.br.foodfacil.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaborService {
    @Autowired
    SaborRepository saborRepository;

    @Transactional
    public ResponseEntity<Object> registrar(SaborRequestDto saborRequestDto) {
        try {
            saborRepository.save(new Sabor(saborRequestDto));
            return new AppUtils().AppCustomJson(MensagemRetorno.ADICIONADO_COM_SUCESSO, Item.SABOR);
        } catch (RuntimeException e) {
            throw new RuntimeException(AppUtils.CustomMensagemExcessao(MensagemRetorno.FALHA_AO_ADICIONAR, e.getMessage()));
        }
    }

    public ResponseEntity<Object> listaTodos(){
        try {
            var list = saborRepository.findAll();
            return ResponseEntity.ok().body(list);
        }catch (RuntimeException e){
            throw new RuntimeException(AppUtils.CustomMensagemExcessao(MensagemRetorno.FALHA_AO_LISTAR,e.getMessage()));
        }
    }


public ResponseEntity<Object> excluir(String id){
        var optional = saborRepository.findById(id);

        if(optional.isEmpty()){
            return new AppUtils().AppCustomJson(MensagemRetorno.ITEM_NAO_EXISTE,Item.SABOR);
        }

        try{
            saborRepository.deleteById(id);
            return new AppUtils().AppCustomJson(MensagemRetorno.EXCLUIDO_COM_SUCESSO,Item.SABOR);
        }catch (RuntimeException e){
            throw new RuntimeException(
                    AppUtils.CustomMensagemExcessao(MensagemRetorno.FALHA_AO_DELETAR, e.getMessage())
            );
        }
    }



   public ResponseEntity<Object> editar(SaborRequestEditDto saborRequestEditDto, String id){

        var optional = saborRepository.findById(id);

        if(optional.isEmpty()){
            return new AppUtils().AppCustomJson(MensagemRetorno.ITEM_NAO_EXISTE,Item.SABOR);
        }

        try {
            var encontrado = optional.get();
            encontrado.setNome(saborRequestEditDto.nome());
            encontrado.setDisponibilidade(saborRequestEditDto.disponibilidade());

            saborRepository.save(encontrado);

            return new AppUtils().AppCustomJson(MensagemRetorno.EDITADO_COM_SUCESSO,Item.SABOR);
        }catch (RuntimeException e){
            throw new RuntimeException(
                    AppUtils.CustomMensagemExcessao(MensagemRetorno.FALHA_AO_EDITAR, e.getMessage()));
        }
    }
}
