package com.app.homeworkoutapplication.module.skill.service.impl;

import com.app.homeworkoutapplication.entity.mapper.SkillMapper;
import com.app.homeworkoutapplication.module.skill.dto.Skill;
import com.app.homeworkoutapplication.module.skill.service.SkillService;
import com.app.homeworkoutapplication.repository.SkillRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    public Skill create(Skill skill) {
        if (skill.getId() != null) {
            throw new BadRequestException("id not null!");
        }
        return skillMapper.toDto(skillRepository.save(skillMapper.toEntity(skill)));
    }

    public Skill update(Skill skill) {
        if (skill.getId() == null) {
            throw new BadRequestException("id null!");
        }
        return skillMapper.toDto(skillRepository.save(skillMapper.toEntity(skill)));
    }

    public void delete(Long id) {
        skillRepository.deleteById(id);
    }
}
