package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.NotificationDTO;
import com.br.foodfacil.ff.dtos.PagamentoBody;
import com.br.foodfacil.ff.dtos.PaymentReceiverDto;
import com.br.foodfacil.ff.services.NotificationService;
import com.br.foodfacil.ff.services.PagamentoService;
import com.br.foodfacil.ff.utils.AppUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(AppUtils.baseUrl + "/pagamento")
public class PagamentoController {
    @Autowired
    NotificationService notificationService;

    @Autowired
    PagamentoService pagamentoService;

    @PostMapping()
    public String makePayment(@RequestBody PaymentReceiverDto paymentReceiverDto) {
        //processar, o pagamento
        //lancei o status como aprovado ou rejeitado no banco de dados
        //envio a notificação com base no resultado
        
        String imagePath = "https://firebasestorage.googleapis.com/v0/b/foodfacil-d0c86.appspot.com/o/app_resources%2Flogo.png?alt=media&token=9ed10677-f17b-4d0c-9159-9e770ad65875";

        notificationService.sendNotificationByToken(
                new NotificationDTO(paymentReceiverDto.deviceToken(),
                        "Pagamento aprovado", "Parabéns," + paymentReceiverDto.userName() + " identificamos que " +
                        "seu pagamento ocorreu com " +
                        "sucesso, " +
                        "seu pedido seguirá em preparo",
                        imagePath,
                        new HashMap<>(){{put("payment_status","approved");}}
                )
                );
        return "Pagamento aprovado";
    }

    @PostMapping("pix")
    ResponseEntity<Object> pagamentoPix(@RequestBody PagamentoBody pagamentoBody){
        System.out.println(pagamentoBody);

        //return ResponseEntity.ok().body(pagamentoBody);
        return  pagamentoService.geraPix(pagamentoBody);
    }

    @PostMapping("notificacao")
    ResponseEntity<Object> noticacao(@RequestParam("id") String id)  {

        System.out.println("recebido");
        System.out.println(id);

        return ResponseEntity.ok().body("ok");
    }
}
