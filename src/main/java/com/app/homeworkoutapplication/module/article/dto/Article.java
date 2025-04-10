package com.app.homeworkoutapplication.module.article.dto;

import com.app.homeworkoutapplication.entity.enumeration.ArticleStatus;
import com.app.homeworkoutapplication.module.industry.dto.Industry;
import com.app.homeworkoutapplication.module.joblevel.dto.JobLevel;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.workingmodel.dto.WorkingModel;
import lombok.Data;

import java.time.Instant;

@Data
public class Article {
    private Long id;
    private String title;
    private String mainImagePath;
    private String content;
    private String requirement;
    private String address;
    private String location;
    private String company;
    private Long fromSalary;
    private Long toSalary;
    private Instant dueDate;
    private ArticleStatus status;
    private Long industryId;
    private Industry industry;
    private Long jobLevelId;
    private JobLevel jobLevel;
    private Long workingModelId;
    private WorkingModel workingModel;
    private Long userId;
    private User user;
    private String mainImageUrl;
}