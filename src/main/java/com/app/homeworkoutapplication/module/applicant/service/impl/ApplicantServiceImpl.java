package com.app.homeworkoutapplication.module.applicant.service.impl;

import com.app.homeworkoutapplication.entity.mapper.ApplicantMapper;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicant.service.ApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.QueryApplicantService;
import com.app.homeworkoutapplication.module.document.dto.Document;
import com.app.homeworkoutapplication.module.document.service.DocumentService;
import com.app.homeworkoutapplication.repository.ApplicantRepository;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import jakarta.mail.Multipart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;
    private final QueryApplicantService queryApplicantService;
    private final DocumentService documentService;
    private final CurrentUserUtil currentUserUtil;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository, ApplicantMapper applicantMapper, QueryApplicantService queryApplicantService, DocumentService documentService, CurrentUserUtil currentUserUtil) {
        this.applicantRepository = applicantRepository;
        this.applicantMapper = applicantMapper;
        this.queryApplicantService = queryApplicantService;
        this.documentService = documentService;
        this.currentUserUtil = currentUserUtil;
    }

    @Override
    public Applicant create(Applicant applicant, List<MultipartFile> files){

        if (applicant.getId() != null) {
            throw new BadRequestException("id not null!");
        }

        applicant.setUserId(currentUserUtil.getCurrentUser().getId());

        Applicant result = applicantMapper.toDto(applicantRepository.save(applicantMapper.toEntity(applicant)));

        for(MultipartFile file : files) {
            Document document = new Document();
            document.setName(file.getName());
            document.setApplicantId(result.getId());
            documentService.create(document, file);
        }

        return result;
    }

    @Override
    public void delete(Long id) {
        applicantRepository.deleteById(id);
    }
} 