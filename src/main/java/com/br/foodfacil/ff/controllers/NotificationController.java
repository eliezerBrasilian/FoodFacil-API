package com.br.foodfacil.ff.controllers;

import com.br.foodfacil.ff.dtos.NotificationDTO;
import com.br.foodfacil.ff.services.NotificationService;
import com.br.foodfacil.ff.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppUtils.baseUrl + "/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping
    public  String sendNotification(@RequestBody NotificationDTO notificationDTO){
        return  notificationService.sendNotificationByToken(notificationDTO);
    }
}
