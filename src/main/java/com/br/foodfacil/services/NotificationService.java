package com.br.foodfacil.services;

import com.br.foodfacil.dtos.NotificacaoRequestDTO;
import com.br.foodfacil.dtos.NotificationDTO;
import org.springframework.http.ResponseEntity;

public interface NotificationService {
    ResponseEntity<Object> excluiTokenSalvoDeCelular(String id);

    ResponseEntity<Object> enviaNotificacoesEmMassa(NotificacaoRequestDTO notificacaoRequestDTO);

    String sendNotificationByToken(NotificationDTO notificationDTO);
}
