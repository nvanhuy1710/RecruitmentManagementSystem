package com.app.homeworkoutapplication.module.document.service.criteria;


import lombok.Data;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;

@Data
public class DocumentCriteria implements Serializable {

    private LongFilter id;

    private StringFilter name;

    private LongFilter applicantId;
}
