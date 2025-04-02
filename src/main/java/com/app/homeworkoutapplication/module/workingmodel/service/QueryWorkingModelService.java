package com.app.homeworkoutapplication.module.workingmodel.service;

import com.app.homeworkoutapplication.module.workingmodel.dto.WorkingModel;
import com.app.homeworkoutapplication.module.workingmodel.service.criteria.WorkingModelCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryWorkingModelService {
    List<WorkingModel> findListByCriteria(WorkingModelCriteria criteria);
    Page<WorkingModel> findPageByCriteria(WorkingModelCriteria criteria, Pageable pageable);
    WorkingModel getById(Long id);
    WorkingModel getByName(String name);
} 