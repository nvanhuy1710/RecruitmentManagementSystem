package com.app.homeworkoutapplication.module.applicant.service.criteria;

import com.app.homeworkoutapplication.entity.filter.ApplicantStatusFilter;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Data
@ParameterObject
public class ApplicantCriteria {
    private LongFilter id;
    private StringFilter fullName;
    private StringFilter phone;
    private ApplicantStatusFilter status;
    private LongFilter articleId;
    private LongFilter userId;
} 