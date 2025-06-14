package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.ArticleEntity;
import com.app.homeworkoutapplication.entity.ArticleJobLevelEntity;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.dto.ArticleJobLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ArticleMapper.class, JobLevelMapper.class})
public interface ArticleJobLevelMapper extends EntityMapper<ArticleJobLevel, ArticleJobLevelEntity> {

    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "jobLevelId", source = "jobLevel.id")
    ArticleJobLevel toDto(ArticleJobLevelEntity entity);

    @Mapping(target = "article.id", source = "articleId")
    @Mapping(target = "jobLevel.id", source = "jobLevelId")
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "jobLevel", ignore = true)
    ArticleJobLevelEntity toEntity(ArticleJobLevel dto);

    default ArticleJobLevelEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArticleJobLevelEntity articleJobLevel = new ArticleJobLevelEntity();
        articleJobLevel.setId(id);
        return articleJobLevel;
    }
} 