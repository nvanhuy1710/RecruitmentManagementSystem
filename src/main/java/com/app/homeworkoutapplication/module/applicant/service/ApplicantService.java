package com.app.homeworkoutapplication.module.applicant.service;

import com.app.homeworkoutapplication.module.applicant.dto.Applicant;

public interface ApplicantService {
    Applicant create(Applicant applicant);
    Applicant update(Applicant applicant);
    void delete(Long id);
} 