package com.app.homeworkoutapplication.module.applicant.service.impl;

import com.app.homeworkoutapplication.entity.mapper.ApplicantMapper;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicant.service.ApplicantService;
import com.app.homeworkoutapplication.repository.ApplicantRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository, ApplicantMapper applicantMapper) {
        this.applicantRepository = applicantRepository;
        this.applicantMapper = applicantMapper;
    }

    @Override
    public Applicant create(Applicant applicant) {
        if (applicant.getId() != null) {
            throw new BadRequestException("id not null!");
        }
        return applicantMapper.toDto(applicantRepository.save(applicantMapper.toEntity(applicant)));
    }

    @Override
    public Applicant update(Applicant applicant) {
        if (applicant.getId() == null) {
            throw new BadRequestException("id null!");
        }
        return applicantMapper.toDto(applicantRepository.save(applicantMapper.toEntity(applicant)));
    }

    @Override
    public void delete(Long id) {
        applicantRepository.deleteById(id);
    }
} 