package com.app.homeworkoutapplication.module.article.dto;

import com.app.homeworkoutapplication.entity.ArticleEntity;
import com.app.homeworkoutapplication.entity.IndustryEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ArticleIndustry {
    private Long id;

    private Long articleId;

    private Long industryId;
}
