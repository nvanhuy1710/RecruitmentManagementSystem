package com.app.homeworkoutapplication.module.skill.service;

import com.app.homeworkoutapplication.module.skill.dto.Skill;

public interface SkillService {

    Skill create(Skill skill);

    Skill update(Skill skill);

    void delete(Long id);
}
