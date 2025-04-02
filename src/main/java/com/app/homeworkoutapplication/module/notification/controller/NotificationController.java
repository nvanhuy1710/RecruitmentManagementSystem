package com.app.homeworkoutapplication.module.notification.controller;


import com.app.homeworkoutapplication.module.notification.dto.NotificationRequest;
import com.app.homeworkoutapplication.module.notification.service.FCMService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/public/api")
public class NotificationController {

    private final FCMService fcmService;

    public NotificationController(FCMService fcmService) {
        this.fcmService = fcmService;
    }

    @GetMapping("/notify")
    public ResponseEntity<Void> testNotify(@RequestParam("token") String token, @RequestParam("message") String message) throws ExecutionException, InterruptedException {
        NotificationRequest request = new NotificationRequest();
        request.setToken(token);
        request.setBody(message);
        request.setTitle("Message");
        fcmService.sendMessageToToken(request);
        return ResponseEntity.noContent().build();
    }
}
