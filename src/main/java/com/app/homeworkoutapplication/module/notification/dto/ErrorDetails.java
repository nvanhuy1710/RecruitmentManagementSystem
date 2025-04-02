package com.app.homeworkoutapplication.module.notification.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {
    LocalDateTime timestamp;
    String message;
    String details;
}
