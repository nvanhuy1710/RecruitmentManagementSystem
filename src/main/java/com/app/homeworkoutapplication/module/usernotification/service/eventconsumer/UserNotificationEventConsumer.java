package com.app.homeworkoutapplication.module.usernotification.service.eventconsumer;

import com.app.homeworkoutapplication.module.usernotification.dto.event.UserNotificationCreatedEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class UserNotificationEventConsumer {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public UserNotificationEventConsumer(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Async("eventTaskExecutor")
    @TransactionalEventListener
    public void handleEvent(UserNotificationCreatedEvent event) {
        try {
            simpMessagingTemplate.convertAndSend("/topic/notification-" + event.getEventData().getUserId(), event.getEventData());
        } catch (Exception e) {
            System.out.println("Error sending notification: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
