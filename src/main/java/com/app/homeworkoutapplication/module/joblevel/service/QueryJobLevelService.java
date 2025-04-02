package com.app.homeworkoutapplication.module.joblevel.service;

import com.app.homeworkoutapplication.module.joblevel.dto.JobLevel;
import com.app.homeworkoutapplication.module.joblevel.service.criteria.JobLevelCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryJobLevelService {

    List<JobLevel> findListByCriteria(JobLevelCriteria criteria);

    Page<JobLevel> findPageByCriteria(JobLevelCriteria criteria, Pageable pageable);

    JobLevel getById(Long id);

    JobLevel getByName(String name);
}
