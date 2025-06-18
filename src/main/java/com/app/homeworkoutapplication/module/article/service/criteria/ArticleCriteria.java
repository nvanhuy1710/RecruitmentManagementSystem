package com.app.homeworkoutapplication.module.article.service.criteria;

import com.app.homeworkoutapplication.entity.filter.ArticleStatusFilter;
import lombok.Data;
import tech.jhipster.service.filter.*;

import java.io.Serializable;
import java.time.Instant;

@Data
public class ArticleCriteria implements Serializable {
    private LongFilter id;
    private StringFilter title;
    private StringFilter content;
    private LongFilter salary;
    private InstantFilter dueDate;
    private ArticleStatusFilter status;
    private BooleanFilter autoCaculate;
    private LongFilter companyId;
    private LongFilter industryId;
    private LongFilter jobLevelId;
    private LongFilter workingModelId;
    private LongFilter userId;
    private Boolean sortByRelated;
}