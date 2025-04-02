package com.app.homeworkoutapplication.module.applicant.service.criteria;

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
    private StringFilter status;
    private LongFilter articleId;
    private LongFilter userId;
} 