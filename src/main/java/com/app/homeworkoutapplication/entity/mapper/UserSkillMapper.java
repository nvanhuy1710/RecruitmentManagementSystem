package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.UserSkillEntity;
import com.app.homeworkoutapplication.module.userskill.dto.UserSkill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, SkillMapper.class})
public interface UserSkillMapper extends EntityMapper<UserSkill, UserSkillEntity> {
} 