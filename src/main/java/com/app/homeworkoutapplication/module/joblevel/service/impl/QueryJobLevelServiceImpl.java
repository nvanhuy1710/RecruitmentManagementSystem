package com.app.homeworkoutapplication.module.joblevel.service.impl;

import com.app.homeworkoutapplication.entity.JobLevelEntity;
import com.app.homeworkoutapplication.entity.JobLevelEntity_;
import com.app.homeworkoutapplication.entity.mapper.JobLevelMapper;
import com.app.homeworkoutapplication.module.joblevel.dto.JobLevel;
import com.app.homeworkoutapplication.module.joblevel.service.QueryJobLevelService;
import com.app.homeworkoutapplication.module.joblevel.service.criteria.JobLevelCriteria;
import com.app.homeworkoutapplication.repository.JobLevelRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryJobLevelServiceImpl extends QueryService<JobLevelEntity> implements QueryJobLevelService {

    private final JobLevelRepository jobLevelRepository;

    private final JobLevelMapper jobLevelMapper;


    public QueryJobLevelServiceImpl(JobLevelRepository jobLevelRepository, JobLevelMapper jobLevelMapper) {
        this.jobLevelRepository = jobLevelRepository;
        this.jobLevelMapper = jobLevelMapper;
    }

    public List<JobLevel> findListByCriteria(JobLevelCriteria criteria) {
        return jobLevelRepository.findAll(createSpecification(criteria)).stream().map(jobLevelMapper::toDto).toList();
    }

    public Page<JobLevel> findPageByCriteria(JobLevelCriteria criteria, Pageable pageable) {
        Page<JobLevelEntity> page =  jobLevelRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(jobLevelMapper::toDto).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    public JobLevel getById(Long id) {
        Optional<JobLevelEntity> job_levelEntity = jobLevelRepository.findById(id);
        if (job_levelEntity.isEmpty()) {
            throw new NotFoundException("Not found job_level by id " + id);
        }
        return jobLevelMapper.toDto(job_levelEntity.get());
    }

    public JobLevel getByName(String name) {
        Optional<JobLevelEntity> job_levelEntity = jobLevelRepository.findByName(name);
        if (job_levelEntity.isEmpty()) {
            throw new NotFoundException("Not found job_level by name " + name);
        }
        return jobLevelMapper.toDto(job_levelEntity.get());
    }


    private Specification<JobLevelEntity> createSpecification(JobLevelCriteria criteria) {
        Specification<JobLevelEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), JobLevelEntity_.id));
        }
        if (criteria.getName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getName(), JobLevelEntity_.name));
        }

        return specification;
    }
}
