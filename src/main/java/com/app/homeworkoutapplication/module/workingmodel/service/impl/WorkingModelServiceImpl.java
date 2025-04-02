package com.app.homeworkoutapplication.module.workingmodel.service.impl;

import com.app.homeworkoutapplication.entity.mapper.WorkingModelMapper;
import com.app.homeworkoutapplication.module.workingmodel.dto.WorkingModel;
import com.app.homeworkoutapplication.module.workingmodel.service.WorkingModelService;
import com.app.homeworkoutapplication.repository.WorkingModelRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class WorkingModelServiceImpl implements WorkingModelService {

    private final WorkingModelRepository workingModelRepository;
    private final WorkingModelMapper workingModelMapper;

    public WorkingModelServiceImpl(WorkingModelRepository workingModelRepository, WorkingModelMapper workingModelMapper) {
        this.workingModelRepository = workingModelRepository;
        this.workingModelMapper = workingModelMapper;
    }

    public WorkingModel create(WorkingModel workingModel) {
        if (workingModel.getId() != null) {
            throw new BadRequestException("id not null!");
        }
        return workingModelMapper.toDto(workingModelRepository.save(workingModelMapper.toEntity(workingModel)));
    }

    public WorkingModel update(WorkingModel workingModel) {
        if (workingModel.getId() == null) {
            throw new BadRequestException("id null!");
        }
        return workingModelMapper.toDto(workingModelRepository.save(workingModelMapper.toEntity(workingModel)));
    }

    public void delete(Long id) {
        workingModelRepository.deleteById(id);
    }
} 