package com.app.homeworkoutapplication.module.article.dto;

import com.app.homeworkoutapplication.entity.enumeration.ArticleStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class Article {
    private Long id;
    private String title;
    private String mainImagePath;
    private String content;
    private String address;
    private String location;
    private String companyWebsiteUrl;
    private Integer salary;
    private Instant dueDate;
    private ArticleStatus status;
    private Long industryId;
    private Long jobLevelId;
    private Long workingModelId;
    private Long userId;
}