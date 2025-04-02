package com.app.homeworkoutapplication.module.industry.service.criteria;


import lombok.Data;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;

@Data
public class IndustryCriteria implements Serializable {

    private LongFilter id;

    private StringFilter name;
}
