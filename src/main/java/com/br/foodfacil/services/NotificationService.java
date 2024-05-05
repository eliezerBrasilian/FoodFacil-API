package com.br.foodfacil.services;

import com.br.foodfacil.dtos.NotificacaoRequestDTO;
import com.br.foodfacil.dtos.NotificationDTO;
import com.br.foodfacil.repositories.TokenDoDispositivoRepository;
import com.google.firebase.FirebaseException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NotificationService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;

    @Autowired
    private TokenDoDispositivoRepository tokenDoDispositivoRepository;

    public ResponseEntity<Object> enviaNotificacoesEmMassa(NotificacaoRequestDTO notificacaoRequestDTO){
        try{
            var tokens = tokenDoDispositivoRepository.findAll();

            tokens.forEach(tokenDoDIspositivo -> {

                var token = tokenDoDIspositivo.getToken();

                System.out.println("token encontrado----------");
                System.out.println(token);

                sendNotificationByToken(new NotificationDTO(
                        token,
                        notificacaoRequestDTO.titulo(),
                        notificacaoRequestDTO.corpo(),
                        notificacaoRequestDTO.imagem(),
                        notificacaoRequestDTO.dados()
                ));
            });
            return ResponseEntity.ok().body(Map.of("message", "notificacao em massa enviada com sucesso"));
        }catch (RuntimeException e){
            throw new RuntimeException("excessao ocorreu ao tentar enviar notificacoes em massa devido a: " + e.getMessage());
        }
    }


    public String sendNotificationByToken(NotificationDTO notificationDTO) {
        var notification = Notification.builder()
                .setTitle(notificationDTO.title())
                .setBody(notificationDTO.body())
                .setImage(notificationDTO.image())
                .build();

        var message = Message.builder()
                .setToken(notificationDTO.token())
                .setNotification(notification)
                .putAllData(notificationDTO.data())
                .build();

        try {
            firebaseMessaging.send(message);
            System.out.println("sendNotificationByToken() -> enviou notificacao");
            return "Success sending notification";
        } catch (FirebaseException e) {
            e.printStackTrace();
            return "Error on seinding notification";
        }
    }
}
