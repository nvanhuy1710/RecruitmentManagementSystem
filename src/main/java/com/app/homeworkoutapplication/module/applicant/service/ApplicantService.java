package com.app.homeworkoutapplication.module.applicant.service;

import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import jakarta.mail.Multipart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApplicantService {
    Applicant create(Applicant applicant, List<MultipartFile> files);
    void delete(Long id);
} 