package com.app.homeworkoutapplication.module.userskill.service;

import com.app.homeworkoutapplication.module.userskill.dto.UserSkill;
import com.app.homeworkoutapplication.module.userskill.service.criteria.UserSkillCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryUserSkillService {
    List<UserSkill> findListByCriteria(UserSkillCriteria criteria);
    Page<UserSkill> findPageByCriteria(UserSkillCriteria criteria, Pageable pageable);
    UserSkill getById(Long id);
} 