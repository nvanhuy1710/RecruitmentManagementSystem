package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.IndustryEntity;
import com.app.homeworkoutapplication.module.industry.dto.Industry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface IndustryMapper extends EntityMapper<Industry, IndustryEntity> {
}
