package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.ApplicantEntity;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {
    Applicant toDto(ApplicantEntity entity);
    ApplicantEntity toEntity(Applicant dto);
} 