package com.app.homeworkoutapplication.module.company.service.criteria;


import lombok.Data;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;

@Data
public class CompanyCriteria implements Serializable {

    private LongFilter id;

    private StringFilter name;

    private StringFilter address;

    private StringFilter location;
}
