package com.app.homeworkoutapplication.module.user.dto;

import com.app.homeworkoutapplication.entity.enumeration.Level;
import com.app.homeworkoutapplication.module.industry.dto.Industry;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class User {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private Instant birthday;

    private Level level;

    private Boolean isActivated;

    private String avatarUrl;

    private String publicAvatarUrl;

    private Long industryId;

    private Industry industry;
}

