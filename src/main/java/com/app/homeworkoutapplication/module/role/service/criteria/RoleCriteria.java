package com.app.homeworkoutapplication.module.role.service.criteria;


import lombok.Data;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;

@Data
public class RoleCriteria implements Serializable {

    private LongFilter id;

    private StringFilter name;
}
