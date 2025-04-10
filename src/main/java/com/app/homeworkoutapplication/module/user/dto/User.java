package com.app.homeworkoutapplication.module.user.dto;

import com.app.homeworkoutapplication.entity.enumeration.Level;
import com.app.homeworkoutapplication.module.industry.dto.Industry;
import com.app.homeworkoutapplication.module.role.dto.Role;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class User {

    private Long id;

    private String email;

    private String fullName;

    private String username;

    private String password;

    private Instant birth;

    private Boolean gender;

    private Boolean isActivated;

    private String avatarPath;

    private String avatarUrl;

    private Long roleId;

    private String roleName;

    private Role role;
}

