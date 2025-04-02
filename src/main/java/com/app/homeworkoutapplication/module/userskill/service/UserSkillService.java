package com.app.homeworkoutapplication.module.userskill.service;

import com.app.homeworkoutapplication.module.userskill.dto.UserSkill;

public interface UserSkillService {

    UserSkill create(UserSkill user_skill);

    UserSkill update(UserSkill user_skill);

    void delete(Long id);
}
