package com.app.homeworkoutapplication.module.skill.service;

import com.app.homeworkoutapplication.module.skill.dto.Skill;
import com.app.homeworkoutapplication.module.skill.service.criteria.SkillCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuerySkillService {

    List<Skill> findListByArticleId(Long articleId);

    List<Skill> findListByCriteria(SkillCriteria criteria);

    Page<Skill> findPageByCriteria(SkillCriteria criteria, Pageable pageable);

    Skill getById(Long id);

    Skill getByName(String name);
}
