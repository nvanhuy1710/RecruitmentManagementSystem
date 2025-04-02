package com.app.homeworkoutapplication.module.workingmodel.service;

import com.app.homeworkoutapplication.module.workingmodel.dto.WorkingModel;

public interface WorkingModelService {
    WorkingModel create(WorkingModel workingModel);
    WorkingModel update(WorkingModel workingModel);
    void delete(Long id);
} 