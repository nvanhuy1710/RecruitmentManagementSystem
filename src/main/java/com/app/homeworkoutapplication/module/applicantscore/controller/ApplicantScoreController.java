package com.app.homeworkoutapplication.module.applicantscore.controller;


import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicant.service.criteria.ApplicantCriteria;
import com.app.homeworkoutapplication.module.applicantscore.dto.ApplicantScore;
import com.app.homeworkoutapplication.module.applicantscore.service.ApplicantScoreService;
import com.app.homeworkoutapplication.module.applicantscore.service.QueryApplicantScoreService;
import com.app.homeworkoutapplication.security.AuthorityConstant;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Authentication")
public class ApplicantScoreController {

    private final ApplicantScoreService applicantScoreService;
    private final QueryApplicantScoreService queryApplicantScoreService;

    public ApplicantScoreController(ApplicantScoreService applicantScoreService, QueryApplicantScoreService queryApplicantScoreService) {
        this.applicantScoreService = applicantScoreService;
        this.queryApplicantScoreService = queryApplicantScoreService;
    }

    @PostMapping(value = "/applicant-scores")
    public ResponseEntity<ApplicantScore> create(
            @Valid @RequestPart("applicant") ApplicantScore applicantScore) throws URISyntaxException {

        ApplicantScore result = applicantScoreService.create(applicantScore);
        return ResponseEntity.created(new URI("/api/applicant-scores/" + result.getId())).body(result);
    }

    @DeleteMapping("/applicant-scores/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        applicantScoreService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
