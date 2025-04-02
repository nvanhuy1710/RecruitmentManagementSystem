package com.app.homeworkoutapplication.module.workingmodel.service.impl;

import com.app.homeworkoutapplication.entity.WorkingModelEntity;
import com.app.homeworkoutapplication.entity.WorkingModelEntity_;
import com.app.homeworkoutapplication.entity.mapper.WorkingModelMapper;
import com.app.homeworkoutapplication.module.workingmodel.dto.WorkingModel;
import com.app.homeworkoutapplication.module.workingmodel.service.QueryWorkingModelService;
import com.app.homeworkoutapplication.module.workingmodel.service.criteria.WorkingModelCriteria;
import com.app.homeworkoutapplication.repository.WorkingModelRepository;
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
public class QueryWorkingModelServiceImpl extends QueryService<WorkingModelEntity> implements QueryWorkingModelService {

    private final WorkingModelRepository workingModelRepository;
    private final WorkingModelMapper workingModelMapper;

    public QueryWorkingModelServiceImpl(WorkingModelRepository workingModelRepository, WorkingModelMapper workingModelMapper) {
        this.workingModelRepository = workingModelRepository;
        this.workingModelMapper = workingModelMapper;
    }

    public List<WorkingModel> findListByCriteria(WorkingModelCriteria criteria) {
        return workingModelRepository.findAll(createSpecification(criteria)).stream().map(workingModelMapper::toDto).toList();
    }

    public Page<WorkingModel> findPageByCriteria(WorkingModelCriteria criteria, Pageable pageable) {
        Page<WorkingModelEntity> page = workingModelRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(workingModelMapper::toDto).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    public WorkingModel getById(Long id) {
        Optional<WorkingModelEntity> workingModelEntity = workingModelRepository.findById(id);
        if (workingModelEntity.isEmpty()) {
            throw new NotFoundException("Not found working model by id " + id);
        }
        return workingModelMapper.toDto(workingModelEntity.get());
    }

    public WorkingModel getByName(String name) {
        Optional<WorkingModelEntity> workingModelEntity = workingModelRepository.findByName(name);
        if (workingModelEntity.isEmpty()) {
            throw new NotFoundException("Not found working model by name " + name);
        }
        return workingModelMapper.toDto(workingModelEntity.get());
    }

    private Specification<WorkingModelEntity> createSpecification(WorkingModelCriteria criteria) {
        Specification<WorkingModelEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), WorkingModelEntity_.id));
        }
        if (criteria.getName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getName(), WorkingModelEntity_.name));
        }

        return specification;
    }
} 