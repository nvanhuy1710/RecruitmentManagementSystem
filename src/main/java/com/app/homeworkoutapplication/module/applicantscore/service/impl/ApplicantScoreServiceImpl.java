package com.app.homeworkoutapplication.module.applicantscore.service.impl;

import com.app.homeworkoutapplication.entity.mapper.ApplicantScoreMapper;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicantscore.dto.ApplicantScore;
import com.app.homeworkoutapplication.module.applicantscore.service.ApplicantScoreService;
import com.app.homeworkoutapplication.module.applicantscore.service.QueryApplicantScoreService;
import com.app.homeworkoutapplication.repository.ApplicantScoreRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApplicantScoreServiceImpl implements ApplicantScoreService {

    private final ApplicantScoreRepository applicantScoreRepository;

    private final ApplicantScoreMapper applicantScoreMapper;

    private final QueryApplicantScoreService queryApplicantScoreService;

    public ApplicantScoreServiceImpl(ApplicantScoreRepository applicantScoreRepository, ApplicantScoreMapper applicantScoreMapper, QueryApplicantScoreService queryApplicantScoreService) {
        this.applicantScoreRepository = applicantScoreRepository;
        this.applicantScoreMapper = applicantScoreMapper;
        this.queryApplicantScoreService = queryApplicantScoreService;
    }

    @Override
    public ApplicantScore create(ApplicantScore applicantScore) {
        if (applicantScore.getApplicantId() == null) {
            throw new BadRequestException("applicant id null!");
        }

        List<ApplicantScore> applicantScore1 = queryApplicantScoreService.findListByApplicantId(applicantScore.getApplicantId());

        if(!applicantScore1.isEmpty()) {
            applicantScore.setId(applicantScore1.get(0).getId());
        }

        ApplicantScore result = applicantScoreMapper.toDto(applicantScoreRepository.save(applicantScoreMapper.toEntity(applicantScore)));
        return result;
    }

    @Override
    public void delete(Long id) {
        applicantScoreRepository.deleteById(id);
    }
}
