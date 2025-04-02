package com.app.homeworkoutapplication.web.rest.error.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
