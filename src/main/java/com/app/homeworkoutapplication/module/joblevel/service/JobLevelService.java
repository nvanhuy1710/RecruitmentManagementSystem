package com.app.homeworkoutapplication.module.joblevel.service;

import com.app.homeworkoutapplication.module.joblevel.dto.JobLevel;

public interface JobLevelService {

    JobLevel create(JobLevel job_level);

    JobLevel update(JobLevel job_level);

    void delete(Long id);
}
