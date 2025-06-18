package com.app.homeworkoutapplication.module.applicant.service;

public interface CaculateApplicantService {

    void caculateMatchScore(Long articleId);

    void caculateMatchScoreByApplicantId(Long applicantId);
}
