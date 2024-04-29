package com.br.foodfacil.services;

import com.br.foodfacil.dtos.NotificationDTO;
import com.google.firebase.FirebaseException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired
    private FirebaseMessaging firebaseMessaging;

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
            return "Success sending notification";
        } catch (FirebaseException e) {
            e.printStackTrace();
            return "Error on seinding notification";
        }
    }
}
