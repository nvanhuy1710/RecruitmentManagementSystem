package com.app.homeworkoutapplication.module.applicantscore.service;

import com.app.homeworkoutapplication.module.applicantscore.dto.ApplicantScore;

public interface ApplicantScoreService {

    ApplicantScore create(ApplicantScore applicantScore);

    void delete(Long id);
}
