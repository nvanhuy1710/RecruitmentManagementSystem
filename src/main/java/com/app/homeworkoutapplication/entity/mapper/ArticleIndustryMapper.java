package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.ArticleEntity;
import com.app.homeworkoutapplication.entity.ArticleIndustryEntity;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.dto.ArticleIndustry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ArticleMapper.class, IndustryMapper.class})
public interface ArticleIndustryMapper extends EntityMapper<ArticleIndustry, ArticleIndustryEntity> {

    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "industryId", source = "industry.id")
    ArticleIndustry toDto(ArticleIndustryEntity entity);

    @Mapping(target = "article.id", source = "articleId")
    @Mapping(target = "industry.id", source = "industryId")
    @Mapping(target = "article", ignore = true)
    @Mapping(target = "industry", ignore = true)
    ArticleIndustryEntity toEntity(ArticleIndustry dto);

    default ArticleIndustryEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArticleIndustryEntity articleIndustry = new ArticleIndustryEntity();
        articleIndustry.setId(id);
        return articleIndustry;
    }
} 