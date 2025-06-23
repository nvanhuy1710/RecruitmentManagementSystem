package com.app.homeworkoutapplication.module.applicantscore.service.criteria;

import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.LongFilter;

@Data
@ParameterObject
public class ApplicantScoreCriteria {

    private LongFilter id;
    private DoubleFilter overall;
    private LongFilter applicantId;
}
