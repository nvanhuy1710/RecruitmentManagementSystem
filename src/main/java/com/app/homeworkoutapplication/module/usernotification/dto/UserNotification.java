package com.app.homeworkoutapplication.module.usernotification.dto;

import com.app.homeworkoutapplication.module.user.dto.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserNotification implements Serializable {

    private Long id;

    private Long articleId;

    private String companyName;

    private String data;

    private Boolean viewed;

    private Long userId;

    private User user;
}
