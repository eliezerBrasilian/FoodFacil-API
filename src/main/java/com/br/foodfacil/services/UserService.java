package com.br.foodfacil.services;


import com.br.foodfacil.dtos.*;
import com.br.foodfacil.enums.Item;
import com.br.foodfacil.enums.MensagemRetorno;
import com.br.foodfacil.enums.TipoDePagamento;
import com.br.foodfacil.models.Pedido;
import com.br.foodfacil.records.Address;
import com.br.foodfacil.records.PagamentoBody;
import com.br.foodfacil.records.ProdutoData;
import com.br.foodfacil.records.UserData;
import com.br.foodfacil.repositories.*;
import com.br.foodfacil.utils.AppUtils;
import com.br.foodfacil.utils.GeraChavePix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CupomRepository cupomRepository;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    SalgadoRepository salgadoRepository;

    @Autowired
    PagamentoService pagamentoService;

    @Autowired
    AcompanhamentoRepository acompanhamentoRepository;

    @Autowired
    TokenDoDispositivoService tokenDoDispositivoService;

    public ResponseEntity<Object> updatePhoto(ProfilePhotoDto profilePhotoDto) {
        var optionalUser = userRepository.findById(profilePhotoDto.userUid());

        try {
            if (optionalUser.isPresent()) {
                System.out.println("usuario existe");
                var user = optionalUser.get();
                user.setProfilePicture(profilePhotoDto.newProfilePhoto());

                userRepository.save(user);
                var data = Map.of("message", "foto de perfil atualizada");
                return ResponseEntity.ok().body(data);
            } else {
                var data = Map.of("message", "usuario não existe");
                return ResponseEntity.ok().body(data);
            }
        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }
    }

    public ResponseEntity<Object> updateAddress(Address address, String userId) {
        var optionalUser = userRepository.findById(userId);

        try {
            if(optionalUser.isEmpty()){
                var data = Map.of("message", "usuario não existe");
                return ResponseEntity.ok().body(data);
            }
           else {
                System.out.println("usuario existe");
                var user = optionalUser.get();
                user.setAddress(address);

                userRepository.save(user);
                var data = Map.of("message", "endereço atualizado");
                return ResponseEntity.ok().body(data);
            }
        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            System.out.println(data);
            return ResponseEntity.badRequest().body(data);
        }
    }

    public ResponseEntity<Object> addCupom(UserCupomDto userCupom) {
        var optionalUser = userRepository.findById(userCupom.userId());
        var optionalCupom = cupomRepository.findById(userCupom.cupom().id());

        try {
            if (optionalUser.isPresent() && optionalCupom.isEmpty()) {
                var data = Map.of("message", "cupom não existe");
                return ResponseEntity.badRequest().body(data);

            } else if (optionalUser.isEmpty() && optionalCupom.isPresent()) {
                var data = Map.of("message", "usuario não existe");
                return ResponseEntity.badRequest().body(data);
            }

            var cupomExpirado = AppUtils.verificaExpiracao(optionalCupom.get().getExpirationDate());
            var cupomRecebido = userCupom.cupom();

             if(cupomExpirado){
                var data = Map.of("message", "cupom está expirado");
                return ResponseEntity.badRequest().body(data);
            }

            var user = optionalUser.get();
            var cupomsExistentes = user.getCupoms();

            if (cupomsExistentes == null) {
                cupomsExistentes = new ArrayList<>();
            }
            else{
                for (SimpleCupomDto cupom_: cupomsExistentes){
                    if(Objects.equals(cupom_.id(), cupomRecebido.id())){
                        var data = Map.of("message", "cupom já está adicionado");
                        System.out.println(data);
                        return ResponseEntity.badRequest().body(data);
                    }
                }
            }

            var newCupom = new SimpleCupomDto(cupomRecebido.id(),
                    cupomRecebido.resgatado(),cupomRecebido.used());

            cupomsExistentes.add(newCupom);
            user.setCupoms(cupomsExistentes);
            userRepository.save(user);

            var data = Map.of("message", "cupom adicionado a conta",
                    "idcupom", cupomRecebido.id(),
                    "userUid", userCupom.userId());

            System.out.println("cupom adicionado");

            return ResponseEntity.ok().body(data);

        } catch (Exception e) {
           /* var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());*/
            System.out.println("mesnsagem de erro");
            System.out.println(e.getMessage());
            System.out.println("causa");
            //System.out.println(e.getCause());
            return ResponseEntity.badRequest().body("data");
        }
    }

    public ResponseEntity<Object> usarCupom(CupomToUpdateDto cupomToUpdateDto) {
        var optionalUser = userRepository.findById(cupomToUpdateDto.userId());
        var optionalCupom = cupomRepository.findById(cupomToUpdateDto.cupomId());

        System.out.println("userId: " + cupomToUpdateDto.userId());
        System.out.println("cupomId: " + cupomToUpdateDto.cupomId());

        try {
            if (optionalUser.isPresent() && optionalCupom.isEmpty()) {
                return ResponseEntity.ok().body(Map.of("message", "cupom não existe"));
            } else if (optionalCupom.isPresent() && optionalUser.isEmpty()) {
                return ResponseEntity.ok().body(Map.of("message", "usuario não existe"));
            }

            var user = optionalUser.get();
            var cupomsList = user.getCupoms();
            SimpleCupomDto newCupom;
            SimpleCupomDto cupomFounded = null;

            for (SimpleCupomDto item : cupomsList) {
                if (Objects.equals(item.id(), cupomToUpdateDto.cupomId())) {
                    System.out.println("encontrou");
                    cupomFounded = item;
                }
            }
            var index = cupomsList.indexOf(cupomFounded);
            cupomsList.set(index,
                    new SimpleCupomDto(cupomFounded.id(), cupomFounded.resgatado(), true));

            user.setCupoms(cupomsList);
            userRepository.save(user);

            var data = Map.of("message", "cupom atualizado",
                    "cupoms", cupomsList);

            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage(), "causa", e.getCause()));
        }
    }

    public ResponseEntity<Object> getCupoms(String userId) {
        var userOptional = userRepository.findById(userId);

        try {
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "usuario nao existe"));
            }

            var cupomsList = userOptional.get().getCupoms();

            return ResponseEntity.ok().body(Map.of(
                    "cupoms", cupomsList));

        } catch (Exception e) {
            var data = Map.of("message", e.getMessage(),
                    "causa", e.getCause());
            return ResponseEntity.badRequest().body(data);
        }

    }

    public ResponseEntity<Object> registraPedido(PedidoRequestDto pedidoRequestDto){
        try{
            var optionalUser = userRepository.findById(pedidoRequestDto.userId());
            if(optionalUser.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","usuario que tentou fazer o pedido não existe no banco de dados"));
            }

            var chavePix = "";
            var pedido = pedidoRepository.save(new Pedido(pedidoRequestDto,chavePix));

            if(pedidoRequestDto.pagamentoEscolhido() == TipoDePagamento.PIX){
                var user = optionalUser.get();
                var userData = new UserData(user.getId(), user.getEmail(), AppUtils.obtemPrimeiroNome(user.getName()));
                var produtoData = new ProdutoData(pedido.getId(), "salgado","salgado",String.valueOf(pedidoRequestDto.total()));

                var pagamentoBody = new PagamentoBody(userData, produtoData);

                System.out.println(pagamentoBody);

                chavePix = new GeraChavePix().generate(pagamentoBody).qrcode();
                pedido.setChavePix(chavePix);
                pedidoRepository.save(pedido);

                return ResponseEntity.ok().body(chavePix);
            }

            else{
                return new AppUtils().AppCustomJson(MensagemRetorno.ADICIONADO_COM_SUCESSO, Item.PEDIDO);
            }


        }catch (Exception e){
            throw new RuntimeException("erro ao salvar pedido devido a uma excessao: "+e.getMessage());
        }
    }

    public ResponseEntity<Object> getPedidos(String userId){
        var optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty() ){
            return ResponseEntity.badRequest().body(Map.of("message","usuário não existe"));
        }

        try {
            var pedidos = pedidoRepository.findByUserId(userId);

            var pedidosResponse = new ArrayList<PedidoDoUsuarioResponseDto>();


            pedidos.forEach(pedido->{
                var salgadosResumidos = new ArrayList<SalgadoResumidoResponseDto>();
                var acompanhamentosResumidos = new ArrayList<AcompanhamentoResumidoResponseDto>();

                var salgados = pedido.getSalgados();
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
                                new SalgadoResumidoResponseDto("INEXISTENTE","INEXISTENTE","INEXISTENTE",
                                        0f,
                                        "INEXISTENTE",0, Collections.emptyList()));
                        //throw new RuntimeException("não existe nenhum salgado com o id, " + salgadoId + "devido a uma excessao");
                    } else {
                        var salgadoEncontrado = optionalSalgado.get();
                        salgadosResumidos.add(
                                new SalgadoResumidoResponseDto(
                                        salgadoEncontrado.getNome(),
                                        salgadoEncontrado.getDescricao(),
                                        salgadoEncontrado.getImagem(),
                                        salgadoEncontrado.getPreco(),
                                        observacao, quantidade, sabores));
                    }
                });

                var acompanhamentos = pedido.getAcompanhamentos();
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

                var newPedido = new PedidoDoUsuarioResponseDto(
                        pedido.getId(),
                        pedido.getUserId(),
                        salgadosResumidos,
                        acompanhamentosResumidos,
                        pedido.getEndereco(),
                        pedido.getPagamentoEscolhido(),
                        pedido.getQuantiaReservada(),
                        pedido.getTotal(),
                        pedido.getCreatedAt(),
                        pedido.getStatus(),
                        pedido.getPagamentoStatus(),
                        pedido.getChavePix()
                );
                pedidosResponse.add(newPedido);

            });

            return ResponseEntity.ok().body(pedidosResponse);
        }catch (RuntimeException e){
            throw new RuntimeException("excessao ocorreu ao tentar buscar os pedidos do usuario: " + e.getMessage());
        }


    }

    public ResponseEntity<Object> salvaOuAtualizaToken(TokenDoDispositivoRequestDto tokenDoDispositivoRequestDto){
        return tokenDoDispositivoService.salvaOuAtualiza(tokenDoDispositivoRequestDto);
    }
}
