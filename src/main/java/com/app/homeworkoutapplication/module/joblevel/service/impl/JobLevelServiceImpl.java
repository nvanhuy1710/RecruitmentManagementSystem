package com.app.homeworkoutapplication.module.joblevel.service.impl;

import com.app.homeworkoutapplication.entity.mapper.JobLevelMapper;
import com.app.homeworkoutapplication.module.joblevel.dto.JobLevel;
import com.app.homeworkoutapplication.module.joblevel.service.JobLevelService;
import com.app.homeworkoutapplication.repository.JobLevelRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class JobLevelServiceImpl implements JobLevelService {

    private final JobLevelRepository jobLevelRepository;

    private final JobLevelMapper jobLevelMapper;

    public JobLevelServiceImpl(JobLevelRepository jobLevelRepository, JobLevelMapper jobLevelMapper) {
        this.jobLevelRepository = jobLevelRepository;
        this.jobLevelMapper = jobLevelMapper;
    }

    public JobLevel create(JobLevel job_level) {
        if (job_level.getId() != null) {
            throw new BadRequestException("id not null!");
        }
        return jobLevelMapper.toDto(jobLevelRepository.save(jobLevelMapper.toEntity(job_level)));
    }

    public JobLevel update(JobLevel job_level) {
        if (job_level.getId() == null) {
            throw new BadRequestException("id null!");
        }
        return jobLevelMapper.toDto(jobLevelRepository.save(jobLevelMapper.toEntity(job_level)));
    }

    public void delete(Long id) {
        jobLevelRepository.deleteById(id);
    }
}
