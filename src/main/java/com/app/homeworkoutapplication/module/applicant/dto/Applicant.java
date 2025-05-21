package com.app.homeworkoutapplication.module.applicant.dto;

import com.app.homeworkoutapplication.entity.enumeration.ApplicantStatus;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.document.dto.Document;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class Applicant {
    private Long id;
    private String fullName;
    private String phone;
    private String coverLetter;
    private Double matchScore;
    private ApplicantStatus status;
    private Instant createdDate;
    private Instant lastModifiedDate;
    private Long articleId;
    private Article article;
    private Long userId;
    private List<Document> documents;
} 