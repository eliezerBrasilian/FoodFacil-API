package com.br.foodfacil.controllers;

import com.br.foodfacil.dtos.NotificacaoRequestDTO;
import com.br.foodfacil.dtos.NotificationDTO;
import com.br.foodfacil.services.IngredienteService;
import com.br.foodfacil.services.NotificationService;
import com.br.foodfacil.services.impl.TokenDoDispositivoServiceImpl;
import com.br.foodfacil.utils.AppUtils;
import com.google.firebase.FirebaseException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173",
        "https://food-facil-painel-admin-8mcqkbvbs-eliezerbrasilians-projects.vercel.app",
        "https://food-facil-painel-admin.vercel.app" +
                "https://foodfacil-website.vercel.app/"})
@RestController
@RequestMapping(AppUtils.baseUrl + "/notificacao")

public class NotificationController {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    @Autowired
    NotificationService notificationService;

    @Autowired
    TokenDoDispositivoServiceImpl tokenDoDispositivoServiceImpl;

    @GetMapping("/tokens-salvos-de-celular")
    public ResponseEntity<Object> getAllTokensDeCelular(){
        return tokenDoDispositivoServiceImpl.getAll();
    }

    @DeleteMapping("/tokens-salvos-de-celular/{id}")
    public ResponseEntity<Object> excluiTokenSalvo(@PathVariable String id){

        return notificationService.excluiTokenSalvoDeCelular(id);
    }

    @PostMapping("/em-massa")
    public ResponseEntity<Object> enviaNotificacoesEmMassa(@RequestBody NotificacaoRequestDTO notificacaoRequestDTO){
        System.out.println(notificacaoRequestDTO);
        return notificationService.enviaNotificacoesEmMassa(notificacaoRequestDTO);
    }

    record DispositivoToken(String dispositivoToken){};
    @PostMapping("/dispositivo")
    public ResponseEntity<Object> enviaNotificacaoParaODispositivo(@RequestBody DispositivoToken dispositivoToken){
        System.out.println("------token");
        System.out.println(dispositivoToken);

        var notification = Notification.builder()
                .setTitle("notificationDTO.title()")
                .setBody("notificationDTO.body()")
                .setImage(null)
                .build();

        var message = Message.builder()
                .setToken(dispositivoToken.dispositivoToken)
                .setNotification(notification)
                .putAllData(Map.of("key","dado"))
                .build();

        try {
            firebaseMessaging.send(message);
            return ResponseEntity.ok().body(Map.of("message","sucesso"));

        } catch (FirebaseException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    // Endpoint donde MercadoPago enviara sus Webhooks (notificaciones de pagos)
    @CrossOrigin(origins = "https://api.mercadopago.com")
    @PostMapping("/mercadopago/webhook")
    public  String sendNotification(@RequestBody NotificationDTO notificationDTO){
        return  notificationService.sendNotificationByToken(notificationDTO);
    }
}
