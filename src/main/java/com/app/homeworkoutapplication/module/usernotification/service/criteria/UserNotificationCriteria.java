package com.app.homeworkoutapplication.module.usernotification.service.criteria;

import lombok.Data;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;

import java.io.Serializable;

@Data
public class UserNotificationCriteria implements Serializable {
    private LongFilter id;
    private LongFilter userId;
    private BooleanFilter viewed;
}
