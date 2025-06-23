package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.ApplicantScoreEntity;
import com.app.homeworkoutapplication.module.applicantscore.dto.ApplicantScore;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ApplicantMapper.class })
public interface ApplicantScoreMapper extends EntityMapper<ApplicantScore, ApplicantScoreEntity> {

    @Mapping(target = "applicantId", source = "applicant.id")
    ApplicantScore toDto(ApplicantScoreEntity entity);

    @Mapping(target = "applicant.id", source = "applicantId")
    ApplicantScoreEntity toEntity(ApplicantScore dto);
}
