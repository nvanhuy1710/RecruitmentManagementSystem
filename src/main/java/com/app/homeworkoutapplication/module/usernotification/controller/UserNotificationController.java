package com.app.homeworkoutapplication.module.usernotification.controller;

import com.app.homeworkoutapplication.module.usernotification.dto.UserNotification;
import com.app.homeworkoutapplication.module.usernotification.service.UserNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class UserNotificationController {

    private final UserNotificationService userNotificationService;

    public UserNotificationController(UserNotificationService userNotificationService) {
        this.userNotificationService = userNotificationService;
    }

    @PutMapping("/notifications/{id}/update-viewed")
    public ResponseEntity<Void> create(@PathVariable Long id) throws URISyntaxException {
        userNotificationService.updateViewed(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/notifications")
    public ResponseEntity<UserNotification> create(@RequestBody UserNotification userNotification) throws URISyntaxException {
        return ResponseEntity.ok(userNotificationService.create(userNotification));
    }
}
