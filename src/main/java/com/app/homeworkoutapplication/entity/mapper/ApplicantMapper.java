package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.ApplicantEntity;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicantMapper {

    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "userId", source = "user.id")
    Applicant toDto(ApplicantEntity entity);

    @Mapping(target = "article.id", source = "articleId")
    @Mapping(target = "user.id", source = "userId")
    ApplicantEntity toEntity(Applicant dto);
} 