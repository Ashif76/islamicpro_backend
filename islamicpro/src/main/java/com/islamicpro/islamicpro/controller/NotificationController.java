package com.islamicpro.islamicpro.controller;

import com.islamicpro.islamicpro.model.NotificationData;
import com.islamicpro.islamicpro.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/islamicpro")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping("/api/send")
    public ResponseEntity sendNotificationToAll(@RequestBody NotificationData notificationData){
        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                notificationService.sendNotification(notificationData);
            }
        });
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
