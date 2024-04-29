package com.br.foodfacil.controllers;

import com.br.foodfacil.dtos.NotificationDTO;
import com.br.foodfacil.dtos.PagamentoBody;
import com.br.foodfacil.dtos.PaymentReceiverDto;
import com.br.foodfacil.services.NotificationService;
import com.br.foodfacil.services.PagamentoService;
import com.br.foodfacil.utils.AppUtils;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
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
    ResponseEntity<Object> pagamentoPix(@RequestBody PagamentoBody pagamentoBody) throws MPException, MPApiException {
        System.out.println(pagamentoBody);

        //return ResponseEntity.ok().body(pagamentoBody);
        return  pagamentoService.geraPix(pagamentoBody);
    }

    @PostMapping("notificacao")
    ResponseEntity<Object> notificacao(@RequestBody Map<String,Object> data)  {

        System.out.println("recebido");
        System.out.println(data);

        return ResponseEntity.ok().body("ok");
    }
}
