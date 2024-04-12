package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.NotificationDTO;
import com.br.foodfacil.ff.dtos.PaymentReceiverDto;
import com.br.foodfacil.ff.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
@RestController
@RequestMapping("/food-facil/api/payment/v1")
public class PaymentController {
    @Autowired
    NotificationService notificationService;

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
}
