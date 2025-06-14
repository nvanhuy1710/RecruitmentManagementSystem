package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.RoleEntity;
import com.app.homeworkoutapplication.entity.SkillEntity;
import com.app.homeworkoutapplication.module.role.dto.Role;
import com.app.homeworkoutapplication.module.skill.dto.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SkillMapper extends EntityMapper<Skill, SkillEntity> {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Skill toDto(SkillEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SkillEntity toEntity(Skill dto);
} 