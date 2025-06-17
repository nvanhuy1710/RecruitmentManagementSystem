package com.app.homeworkoutapplication.module.user.service.criteria;

import lombok.Data;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;

@Data
public class UserCriteria implements Serializable {

    private LongFilter id;

    private StringFilter email;

    private StringFilter fullName;

    private StringFilter username;

    private BooleanFilter employee;

    private BooleanFilter locked;

    private InstantFilter birth;
}
