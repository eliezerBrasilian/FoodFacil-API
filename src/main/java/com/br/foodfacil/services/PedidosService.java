package com.br.foodfacil.services;

import com.br.foodfacil.dtos.*;
import com.br.foodfacil.enums.PagamentoStatus;
import com.br.foodfacil.enums.PedidoStatus;
import com.br.foodfacil.enums.Plataforma;
import com.br.foodfacil.records.Address;
import com.br.foodfacil.repositories.AcompanhamentoRepository;
import com.br.foodfacil.repositories.PedidoRepository;
import com.br.foodfacil.repositories.SalgadoRepository;
import com.br.foodfacil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

@Service
public class PedidosService {
    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    SalgadoRepository salgadoRepository;

    @Autowired
    AcompanhamentoRepository acompanhamentoRepository;

    @Autowired
    UserRepository userRepository;


    public ResponseEntity<Object> edita(PedidoRequestEditDto pedidoRequestEditDto, String id){

        var optionalPedido = pedidoRepository.findById(id);

        if(optionalPedido.isEmpty()){
            var data = Map.of("message","pedido nao existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
        }

        try {
            var pedidoEncontrado = optionalPedido.get();
            pedidoEncontrado.setStatus(pedidoRequestEditDto.pedidoStatus());

            pedidoRepository.save(pedidoEncontrado);

            var data = Map.of("message", "pedido atualizado com sucesso no banco de dados");

            return ResponseEntity.ok().body(data);
        }catch (RuntimeException e){
            throw new RuntimeException("falha ao editar pedido devido a uma excessao: "+e.getMessage());
        }
    }

    public ResponseEntity<Object> getAll(){

        try {
            var list = pedidoRepository.findAll();

            var salgadosResumidos = new ArrayList<SalgadoResumidoResponseDto>();
            var acompanhamentosResumidos = new ArrayList<AcompanhamentoResumidoResponseDto>();

            var pedidosResponseDto = new ArrayList<PedidoResponseDto>();

            list.forEach(p->{
                var pedidoId = p.getId();
                var userId = p.getUserId();

                //----------
                var salgados = p.getSalgados();
                salgados.forEach(s->{
                    var salgadoId = s.id();
                    var sabores = s.sabores();
                    var observacao = s.observacao();
                    var quantidade = s.quantidade();
                    //buscando salgado
                   var optionalSalgado = salgadoRepository.findById(salgadoId);
                   if(optionalSalgado.isEmpty()){
                       System.out.println("não existe nenhum salgado com o id, " + salgadoId);
                       salgadosResumidos.add(
                               new SalgadoResumidoResponseDto("INEXISTENTE","INEXISTENTE",
                                       0f,
                                       "INEXISTENTE",0, Collections.emptyList()));
                       //throw new RuntimeException("não existe nenhum salgado com o id, " + salgadoId + "devido a uma excessao");
                   } else {
                       var salgadoEncontrado = optionalSalgado.get();
                       salgadosResumidos.add(
                               new SalgadoResumidoResponseDto(
                                       salgadoEncontrado.getNome(),
                                       salgadoEncontrado.getImagem(),
                                       salgadoEncontrado.getPreco(),
                                       observacao, quantidade, sabores));
                   }
                });
                //----------
                var acompanhamentos = p.getAcompanhamentos();
                acompanhamentos.forEach(a->{
                    var acompanhamentoId = a.id();
                    var quantidade = a.quantidade();

                    var optionalAcomp = acompanhamentoRepository.findById(acompanhamentoId);
                    if(optionalAcomp.isEmpty()) {
                        System.out.println("não existe nenhum acompanhamento com o id, " + acompanhamentoId);
                        //throw new RuntimeException("falha ao trazer pedidos devido a uma excessao");
                        acompanhamentosResumidos.add(
                                new AcompanhamentoResumidoResponseDto(
                                        "INEXISTENTE","INEXISTENTE",0f,0
                                )
                        );
                    }
                    else{
                        var acompEncontrado = optionalAcomp.get();
                        var nome = acompEncontrado.getNome();
                        var descricao = acompEncontrado.getDescricao();
                        var preco = acompEncontrado.getPreco();
                        acompanhamentosResumidos.add(
                                new AcompanhamentoResumidoResponseDto(
                                        nome,descricao,preco,quantidade
                                )
                        );
                    }
                });
                //----------
                var endereco = new Address(p.getEndereco().cidade(),
                        p.getEndereco().rua(),
                        p.getEndereco().numero(),
                        p.getEndereco().bairro(),
                        p.getEndereco().complemento()
                        );
                //----------
                var optionalUser = userRepository.findById(userId);
                UserResponseDto userResponse = null;
                if(optionalUser.isPresent()){
                    var user = optionalUser.get();

                    userResponse = new UserResponseDto(userId, user.getName(), user.getEmail());
                }

                        var pagamentoEscolhido = p.getPagamentoEscolhido();
                        float quantiaReservada = p.getQuantiaReservada();
                        Plataforma plataforma = p.getPlataforma();
                        String dispositivoToken = p.getDispositivoToken();
                        float total = p.getTotal();
                        long createdAt = p.getCreatedAt();
                        PedidoStatus status = p.getStatus();
                        PagamentoStatus pagamentoStatus = p.getPagamentoStatus();

                        pedidosResponseDto.add(new PedidoResponseDto(
                                pedidoId,
                                userResponse,
                                salgadosResumidos,
                                acompanhamentosResumidos,
                                endereco,
                                pagamentoEscolhido,
                                quantiaReservada,
                                plataforma,
                                dispositivoToken,
                                total,
                                createdAt,
                                status,
                                pagamentoStatus
                        ));
            }
            );

            var data = Map.of("message", "todos pedidos",
                    "lista", pedidosResponseDto);

            return ResponseEntity.ok().body(data);
        }catch (RuntimeException e){
            throw new RuntimeException("falha ao trazer pedidos devido a uma excessao: "+e.getMessage());
        }
    }
}
