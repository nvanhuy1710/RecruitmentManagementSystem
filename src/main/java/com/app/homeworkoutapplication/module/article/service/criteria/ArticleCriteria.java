package com.app.homeworkoutapplication.module.article.service.criteria;

import com.app.homeworkoutapplication.entity.filter.ArticleStatusFilter;
import lombok.Data;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.time.Instant;

@Data
public class ArticleCriteria implements Serializable {
    private LongFilter id;
    private StringFilter title;
    private StringFilter content;
    private StringFilter address;
    private StringFilter location;
    private IntegerFilter salary;
    private InstantFilter dueDate;
    private ArticleStatusFilter status;
    private LongFilter industryId;
    private LongFilter jobLevelId;
    private LongFilter workingModelId;
    private LongFilter userId;
}