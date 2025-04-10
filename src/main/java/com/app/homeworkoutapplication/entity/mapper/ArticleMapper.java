package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.ArticleEntity;
import com.app.homeworkoutapplication.module.article.dto.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(target = "industryId", source = "industry.id")
    @Mapping(target = "jobLevelId", source = "jobLevel.id")
    @Mapping(target = "workingModelId", source = "workingModel.id")
    @Mapping(target = "userId", source = "user.id")
    Article toDto(ArticleEntity entity);

    @Mapping(target = "industry.id", source = "industryId")
    @Mapping(target = "jobLevel.id", source = "jobLevelId")
    @Mapping(target = "workingModel.id", source = "workingModelId")
    @Mapping(target = "user.id", source = "userId")
    ArticleEntity toEntity(Article dto);
} 