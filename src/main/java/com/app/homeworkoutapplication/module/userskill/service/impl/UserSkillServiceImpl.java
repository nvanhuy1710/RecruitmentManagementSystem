package com.app.homeworkoutapplication.module.userskill.service.impl;

import com.app.homeworkoutapplication.entity.mapper.UserSkillMapper;
import com.app.homeworkoutapplication.module.userskill.dto.UserSkill;
import com.app.homeworkoutapplication.module.userskill.service.UserSkillService;
import com.app.homeworkoutapplication.repository.UserSkillRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    private final UserSkillRepository user_skillRepository;

    private final UserSkillMapper user_skillMapper;

    public UserSkillServiceImpl(UserSkillRepository user_skillRepository, UserSkillMapper user_skillMapper) {
        this.user_skillRepository = user_skillRepository;
        this.user_skillMapper = user_skillMapper;
    }

    public UserSkill create(UserSkill user_skill) {
        if (user_skill.getId() != null) {
            throw new BadRequestException("id not null!");
        }
        return user_skillMapper.toDto(user_skillRepository.save(user_skillMapper.toEntity(user_skill)));
    }

    public UserSkill update(UserSkill user_skill) {
        if (user_skill.getId() == null) {
            throw new BadRequestException("id null!");
        }
        return user_skillMapper.toDto(user_skillRepository.save(user_skillMapper.toEntity(user_skill)));
    }

    public void delete(Long id) {
        user_skillRepository.deleteById(id);
    }
}
