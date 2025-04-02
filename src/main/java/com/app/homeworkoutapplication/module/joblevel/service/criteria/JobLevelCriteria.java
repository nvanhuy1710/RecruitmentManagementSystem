package com.app.homeworkoutapplication.module.joblevel.service.criteria;


import lombok.Data;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;

@Data
public class JobLevelCriteria implements Serializable {

    private LongFilter id;

    private StringFilter name;
}
