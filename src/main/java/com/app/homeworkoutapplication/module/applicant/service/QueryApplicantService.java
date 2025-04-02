package com.app.homeworkoutapplication.module.applicant.service;

import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicant.service.criteria.ApplicantCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryApplicantService {
    List<Applicant> findListByCriteria(ApplicantCriteria criteria);
    Page<Applicant> findPageByCriteria(ApplicantCriteria criteria, Pageable pageable);
    Applicant getById(Long id);
} 