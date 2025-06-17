package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.ApplicantEntity;
import com.app.homeworkoutapplication.entity.ArticleIndustryEntity;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.article.dto.ArticleIndustry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicantMapper extends EntityMapper<Applicant, ApplicantEntity> {

    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "documents", ignore = true)
    Applicant toDto(ApplicantEntity entity);

    @Mapping(target = "article.id", source = "articleId")
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "documents", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    ApplicantEntity toEntity(Applicant dto);
} 