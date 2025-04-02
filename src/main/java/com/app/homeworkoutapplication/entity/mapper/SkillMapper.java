package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.SkillEntity;
import com.app.homeworkoutapplication.module.skill.dto.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Skill toDto(SkillEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SkillEntity toEntity(Skill dto);
} 