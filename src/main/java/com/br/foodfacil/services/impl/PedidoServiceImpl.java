package com.br.foodfacil.services.impl;

import com.br.foodfacil.dtos.*;
import com.br.foodfacil.enums.*;
import com.br.foodfacil.records.Address;
import com.br.foodfacil.repositories.AcompanhamentoRepository;
import com.br.foodfacil.repositories.PedidoRepository;
import com.br.foodfacil.repositories.SalgadoRepository;
import com.br.foodfacil.repositories.UserRepository;
import com.br.foodfacil.services.NotificationService;
import com.br.foodfacil.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@Service
public class PedidoServiceImpl {
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    SalgadoRepository salgadoRepository;

    @Autowired
    AcompanhamentoRepository acompanhamentoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationService notificationService;

    public ResponseEntity<Object> buscaPedido(String id){
        try{
            var optionalPedido = pedidoRepository.findById(id);
            if(optionalPedido.isEmpty()){
                return new AppUtils().AppCustomJson(MensagemRetorno.FALHA_AO_LISTAR, Item.PEDIDO);
            }
            else{
                    var pedido = optionalPedido.get();

                    var newPedido = new PedidoDoUsuarioResponseDto(
                            pedido.getId(),
                            pedido.getUserId(),
                            pedido.getSalgados(),
                            pedido.getAcompanhamentos(),
                            pedido.getEndereco(),
                            pedido.getPagamentoEscolhido(),
                            pedido.getQuantiaReservada(),
                            pedido.getTotal(),
                            pedido.getCreatedAt(),
                            pedido.getStatus(),
                            pedido.getPagamentoStatus(),
                            pedido.getChavePix(),
                            pedido.getTaxa()
                    );

                return ResponseEntity.ok().body(newPedido);
            }
        }catch(RuntimeException e){
            throw new RuntimeException("");
        }
    }

    public ResponseEntity<Object> confirmaPagamento(String id){
        var optionalPedido = pedidoRepository.findById(id);

        if(optionalPedido.isEmpty()){
            var data = Map.of("message","pedido nao existe");

            //todo fazer reembolso nesse cenario
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
        }

        try {
            var pedidoEncontrado = optionalPedido.get();
            pedidoEncontrado.setPagamentoStatus(PagamentoStatus.PAGAMENTO_APROVADO);
            pedidoEncontrado.setStatus(PedidoStatus.AGUARDANDO_PREPARO);
            pedidoEncontrado.setPayedAt(System.currentTimeMillis());
            pedidoRepository.save(pedidoEncontrado);

            var dispositivoToken = pedidoEncontrado.getDispositivoToken();

            if(dispositivoToken!= null){

                var body = "Seu pagamento foi aprovado com sucesso 😍";

                var notificacao = new NotificationDTO
                        (dispositivoToken,"Atualização no seu pedido",body,"",Map.of());

                try{
                    notificationService.sendNotificationByToken(notificacao);
                    System.out.println("notitificacao enviada com sucesso !");
                }catch (RuntimeException e){
                    System.out.println("não foi possivel enviar notificação para o dispostivido");
                }
            }

            var data = Map.of("message", "pagamento foi confirmado com sucesso no banco de dados");

            return ResponseEntity.ok().body(data);
        }catch (RuntimeException e){
            throw new RuntimeException("falha ao confirmar pagamento do pedido devido a uma excessao: "+e.getMessage());
        }
    }

    public ResponseEntity<Object> editaStatus(PedidoRequestEditDto pedidoRequestEditDto, String id){

        var optionalPedido = pedidoRepository.findById(id);

        if(optionalPedido.isEmpty()){
            var data = Map.of("message","pedido nao existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
        }

        try {
            var pedidoEncontrado = optionalPedido.get();
            pedidoEncontrado.setStatus(pedidoRequestEditDto.pedidoStatus());
            pedidoRepository.save(pedidoEncontrado);

            var dispositivoToken = pedidoRequestEditDto.dispositivoToken();

            if(dispositivoToken!= null){

                var body = "";
                var ps = pedidoRequestEditDto.pedidoStatus();

                body = switch (ps) {
                    case EM_PREPARO -> "Começamos a preparar seu pedido";
                    case FINALIZADO -> "Uhuu, terminamos de preparar seu pedido";
                    case SAIU_PARA_ENTREGA -> "Seu pedido saiu para entrega 😍";
                    case CHEGOU_NO_ENDERECO -> "Tok tok, seu pedido chegou 😊✅";
                    default -> body;
                };

                var notificacao = new NotificationDTO
                        (dispositivoToken,"Atualização no seu pedido",body,"",Map.of());

                notificationService.sendNotificationByToken(notificacao);
            }

            var data = Map.of("message", "pedido atualizado com sucesso no banco de dados");

            return ResponseEntity.ok().body(data);
        }catch (RuntimeException e){
            throw new RuntimeException("falha ao editar pedido devido a uma excessao: "+e.getMessage());
        }
    }

    public ResponseEntity<Object> getAll(){
        try {
            var pedidos = pedidoRepository.findAll();

            return ResponseEntity.ok().body(pedidos);
        }catch (RuntimeException e){
            throw new RuntimeException("falha ao trazer pedidos devido a uma excessao: "+e.getMessage());
        }
    }

    public ResponseEntity<Object> exclui(String id){
        var optionalSalgado = pedidoRepository.findById(id);

        if(optionalSalgado.isEmpty()){
            return new AppUtils().AppCustomJson(MensagemRetorno.ITEM_NAO_EXISTE, Item.PEDIDO);
        }

        try{
            pedidoRepository.deleteById(id);
            return new AppUtils().AppCustomJson(MensagemRetorno.EXCLUIDO_COM_SUCESSO, Item.PEDIDO);
        }catch (RuntimeException e){
            throw new RuntimeException(AppUtils.CustomMensagemExcessao(MensagemRetorno.FALHA_AO_DELETAR,e.getMessage()));
        }
    }
}
