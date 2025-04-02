package com.app.homeworkoutapplication.entity.mapper;


import com.app.homeworkoutapplication.entity.JobLevelEntity;
import com.app.homeworkoutapplication.module.joblevel.dto.JobLevel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface JobLevelMapper extends EntityMapper<JobLevel, JobLevelEntity> {
}
