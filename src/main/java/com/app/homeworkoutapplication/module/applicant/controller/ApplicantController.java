package com.app.homeworkoutapplication.module.applicant.controller;

import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicant.service.ApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.QueryApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.criteria.ApplicantCriteria;
import com.app.homeworkoutapplication.security.AuthorityConstant;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class ApplicantController {

    private final ApplicantService applicantService;
    private final QueryApplicantService queryApplicantService;

    public ApplicantController(ApplicantService applicantService, QueryApplicantService queryApplicantService) {
        this.applicantService = applicantService;
        this.queryApplicantService = queryApplicantService;
    }

    @PostMapping(value = "/applicants")
    public ResponseEntity<Applicant> create(
            @Valid @RequestPart("applicant") Applicant applicant,
            @RequestPart("files") List<MultipartFile> files)
            throws URISyntaxException {

        Applicant result = applicantService.create(applicant, files);
        return ResponseEntity.created(new URI("/api/applicants/" + result.getId())).body(result);
    }

    @GetMapping("/applicants")
    public ResponseEntity<List<Applicant>> getApplicantPages(@ParameterObject ApplicantCriteria criteria, @ParameterObject Pageable pageable) {
        Page<Applicant> page = queryApplicantService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/applicants/all")
    public ResponseEntity<List<Applicant>> getAllApplicants(@ParameterObject ApplicantCriteria criteria) {
        List<Applicant> applicants = queryApplicantService.findListByCriteria(criteria);
        return ResponseEntity.ok(applicants);
    }

    @GetMapping("/applicants/{id}")
    public ResponseEntity<Applicant> getById(@PathVariable("id") Long id) {
        Applicant res = queryApplicantService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/applicants/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        applicantService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 