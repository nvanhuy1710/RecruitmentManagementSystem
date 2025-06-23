package com.app.homeworkoutapplication.module.applicant.service.impl;

import com.app.homeworkoutapplication.entity.enumeration.ApplicantStatus;
import com.app.homeworkoutapplication.entity.mapper.ApplicantMapper;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicant.dto.event.ApplicantAcceptEvent;
import com.app.homeworkoutapplication.module.applicant.dto.event.ApplicantCreatedEvent;
import com.app.homeworkoutapplication.module.applicant.dto.event.ApplicantRejectEvent;
import com.app.homeworkoutapplication.module.applicant.service.ApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.QueryApplicantService;
import com.app.homeworkoutapplication.module.document.dto.Document;
import com.app.homeworkoutapplication.module.document.service.DocumentService;
import com.app.homeworkoutapplication.module.mail.service.MailService;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.repository.ApplicantRepository;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;
    private final QueryApplicantService queryApplicantService;
    private final DocumentService documentService;
    private final CurrentUserUtil currentUserUtil;
    private final QueryUserService queryUserService;
    private final MailService mailService;
    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository, ApplicantMapper applicantMapper, QueryApplicantService queryApplicantService, DocumentService documentService, CurrentUserUtil currentUserUtil, QueryUserService queryUserService, MailService mailService, ObjectMapper objectMapper, ApplicationEventPublisher applicationEventPublisher) {
        this.applicantRepository = applicantRepository;
        this.applicantMapper = applicantMapper;
        this.queryApplicantService = queryApplicantService;
        this.documentService = documentService;
        this.currentUserUtil = currentUserUtil;
        this.queryUserService = queryUserService;
        this.mailService = mailService;
        this.objectMapper = objectMapper;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Applicant create(Applicant applicant, List<MultipartFile> files){

        if (applicant.getId() != null) {
            throw new BadRequestException("id not null!");
        }

        applicant.setUserId(currentUserUtil.getCurrentUser().getId());
        applicant.setStatus(ApplicantStatus.SUBMITTED);

        Applicant result = applicantMapper.toDto(applicantRepository.save(applicantMapper.toEntity(applicant)));

        for(MultipartFile file : files) {
            Document document = new Document();
            document.setName(file.getOriginalFilename());
            document.setApplicantId(result.getId());
            documentService.create(document, file);
        }

        applicationEventPublisher.publishEvent(new ApplicantCreatedEvent(this, result));

        return result;
    }

    @Override
    public Applicant updateScore(Long id, Double score) {
        Applicant applicant = queryApplicantService.getById(id);
        applicant.setMatchScore(score);
        return applicantMapper.toDto(applicantRepository.save(applicantMapper.toEntity(applicant)));
    }

    @Override
    public void accept(Long id) {
        Applicant applicant = queryApplicantService.getById(id);
        applicant.setStatus(ApplicantStatus.ACCEPTED);
        applicantRepository.save(applicantMapper.toEntity(applicant));
        applicationEventPublisher.publishEvent(new ApplicantAcceptEvent(this, applicant));
    }

    @Override
    public void decline(Long id) {
        Applicant applicant = queryApplicantService.getById(id);
        applicant.setStatus(ApplicantStatus.DECLINED);
        applicantRepository.save(applicantMapper.toEntity(applicant));
        applicationEventPublisher.publishEvent(new ApplicantRejectEvent(this, applicant));
    }

    @Override
    public void delete(Long id) {
        applicantRepository.deleteById(id);
    }
} 