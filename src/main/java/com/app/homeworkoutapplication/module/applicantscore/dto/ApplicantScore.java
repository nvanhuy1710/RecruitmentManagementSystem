package com.app.homeworkoutapplication.module.applicantscore.dto;

import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import lombok.Data;

@Data
public class ApplicantScore {

    private Long id;

    private Double overall;

    private Double structure;

    private Double skill;

    private Double experience;

    private Double education;

    private Long applicantId;

    private Applicant applicant;
}
