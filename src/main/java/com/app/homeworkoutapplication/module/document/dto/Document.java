package com.app.homeworkoutapplication.module.document.dto;

import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import lombok.Data;

@Data
public class Document {
    private Long id;

    private String name;

    private String filePath;

    private Long applicantId;

    private Applicant applicant;
}
