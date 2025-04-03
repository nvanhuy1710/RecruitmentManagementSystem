package com.app.homeworkoutapplication.module.applicant.dto;

import com.app.homeworkoutapplication.module.document.dto.Document;
import lombok.Data;

import java.util.List;

@Data
public class Applicant {
    private Long id;
    private String fullName;
    private String phone;
    private String coverLetter;
    private String status;
    private String resumePath;
    private Long articleId;
    private Long userId;
} 