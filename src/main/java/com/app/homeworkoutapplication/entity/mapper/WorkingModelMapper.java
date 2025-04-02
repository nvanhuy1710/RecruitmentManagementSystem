package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.WorkingModelEntity;
import com.app.homeworkoutapplication.module.workingmodel.dto.WorkingModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface WorkingModelMapper extends EntityMapper<WorkingModel, WorkingModelEntity> {
} 