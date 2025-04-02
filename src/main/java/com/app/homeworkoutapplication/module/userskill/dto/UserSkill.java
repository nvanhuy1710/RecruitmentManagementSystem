package com.app.homeworkoutapplication.module.userskill.dto;

import com.app.homeworkoutapplication.module.skill.dto.Skill;
import com.app.homeworkoutapplication.module.user.dto.User;
import lombok.Data;

@Data
public class UserSkill {
    private Long id;

    private Long userId;

    private Long skillId;

    private User user;

    private Skill skill;
}
