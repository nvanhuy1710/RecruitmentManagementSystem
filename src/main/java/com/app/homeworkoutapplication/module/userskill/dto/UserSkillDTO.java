package com.app.homeworkoutapplication.module.userskill.dto;

import lombok.Data;

@Data
public class UserSkillDTO {
    private Long id;
    private Long userId;
    private Long skillId;
    private Integer level;
} 