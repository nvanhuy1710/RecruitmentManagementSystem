package com.app.homeworkoutapplication.module.userskill.service.impl;

import com.app.homeworkoutapplication.entity.mapper.UserSkillMapper;
import com.app.homeworkoutapplication.module.userskill.dto.UserSkill;
import com.app.homeworkoutapplication.module.userskill.service.UserSkillService;
import com.app.homeworkoutapplication.repository.UserSkillRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserSkillMapper userSkillMapper;

    public UserSkillServiceImpl(UserSkillRepository userSkillRepository, UserSkillMapper userSkillMapper) {
        this.userSkillRepository = userSkillRepository;
        this.userSkillMapper = userSkillMapper;
    }

    public UserSkill create(UserSkill userSkill) {
        if (userSkill.getId() != null) {
            throw new BadRequestException("id not null!");
        }
        return userSkillMapper.toDto(userSkillRepository.save(userSkillMapper.toEntity(userSkill)));
    }

    public UserSkill update(UserSkill userSkill) {
        if (userSkill.getId() == null) {
            throw new BadRequestException("id null!");
        }
        return userSkillMapper.toDto(userSkillRepository.save(userSkillMapper.toEntity(userSkill)));
    }

    public void delete(Long id) {
        userSkillRepository.deleteById(id);
    }
} 