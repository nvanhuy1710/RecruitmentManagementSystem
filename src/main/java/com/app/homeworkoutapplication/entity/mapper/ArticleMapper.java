package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.ArticleEntity;
import com.app.homeworkoutapplication.entity.CompanyEntity;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.company.dto.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper extends EntityMapper<Article, ArticleEntity> {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "industries", ignore = true)
    @Mapping(target = "jobLevels", ignore = true)
    @Mapping(target = "workingModels", ignore = true)
    Article toDto(ArticleEntity entity);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "company.id", source = "companyId")
    @Mapping(target = "industries", ignore = true)
    @Mapping(target = "jobLevels", ignore = true)
    @Mapping(target = "workingModels", ignore = true)
    ArticleEntity toEntity(Article dto);
}