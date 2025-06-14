package com.app.homeworkoutapplication.module.article.dto;

import com.app.homeworkoutapplication.entity.enumeration.ArticleStatus;
import com.app.homeworkoutapplication.module.company.dto.Company;
import com.app.homeworkoutapplication.module.industry.dto.Industry;
import com.app.homeworkoutapplication.module.joblevel.dto.JobLevel;
import com.app.homeworkoutapplication.module.skill.dto.Skill;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.workingmodel.dto.WorkingModel;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Data
public class Article implements Serializable {
    private Long id;
    private String title;
    private String mainImagePath;
    private String content;
    private String requirement;
    private String location;
    private Long fromSalary;
    private Long toSalary;
    private Instant dueDate;
    private ArticleStatus status;
    private Long companyId;
    private Company company;
    private List<Long> industryIds;
    private List<Industry> industries;
    private List<Long> jobLevelIds;
    private List<JobLevel> jobLevels;
    private List<Long> workingModelIds;
    private List<WorkingModel> workingModels;
    private List<Long> skillIds;
    private List<Skill> skills;
    private Long userId;
    private User user;
    private String mainImageUrl;
}