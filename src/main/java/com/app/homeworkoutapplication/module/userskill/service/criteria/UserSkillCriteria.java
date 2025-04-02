package com.app.homeworkoutapplication.module.userskill.service.criteria;


import lombok.Data;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

import java.io.Serializable;

@Data
public class UserSkillCriteria implements Serializable {

    private LongFilter id;

    private LongFilter userId;

    private LongFilter skillId;
}
