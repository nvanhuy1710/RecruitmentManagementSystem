package com.app.homeworkoutapplication.module.account.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private Boolean gender;
    private Instant birth;
}
