package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.ArticleJobLevelEntity;
import com.app.homeworkoutapplication.entity.ArticleWorkingModelEntity;
import com.app.homeworkoutapplication.module.article.dto.ArticleJobLevel;
import com.app.homeworkoutapplication.module.article.dto.ArticleWorkingModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ArticleMapper.class, WorkingModelMapper.class})
public interface ArticleWorkingModelMapper extends EntityMapper<ArticleWorkingModel, ArticleWorkingModelEntity> {

    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "workingModelId", source = "workingModel.id")
    ArticleWorkingModel toDto(ArticleWorkingModelEntity entity);

    @Mapping(target = "article.id", source = "articleId")
    @Mapping(target = "workingModel.id", source = "workingModelId")
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "workingModel", ignore = true)
    ArticleWorkingModelEntity toEntity(ArticleWorkingModel dto);

    default ArticleWorkingModelEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArticleWorkingModelEntity articleWorkingModel = new ArticleWorkingModelEntity();
        articleWorkingModel.setId(id);
        return articleWorkingModel;
    }
} 