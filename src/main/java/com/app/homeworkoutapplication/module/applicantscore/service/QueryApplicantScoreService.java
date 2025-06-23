package com.app.homeworkoutapplication.module.applicantscore.service;

import com.app.homeworkoutapplication.module.applicantscore.dto.ApplicantScore;
import com.app.homeworkoutapplication.module.applicantscore.service.criteria.ApplicantScoreCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QueryApplicantScoreService {

    List<ApplicantScore> findListByCriteria(ApplicantScoreCriteria criteria);
    List<ApplicantScore> findListByApplicantId(Long applicantId);
    Page<ApplicantScore> findPageByCriteria(ApplicantScoreCriteria criteria, Pageable pageable);
    ApplicantScore getById(Long id);

    Optional<ApplicantScore> findById(Long id);
}
