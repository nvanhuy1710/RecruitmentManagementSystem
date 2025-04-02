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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/applicants")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Applicant> create(@Valid @RequestBody Applicant applicant) throws URISyntaxException {
        Applicant result = applicantService.create(applicant);
        return ResponseEntity.created(new URI("/api/applicants/" + result.getId())).body(result);
    }

    @PutMapping("/applicants/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Applicant> update(@PathVariable("id") Long id, @Valid @RequestBody Applicant applicant) {
        if (applicant.getId() == null) applicant.setId(id);
        Applicant res = applicantService.update(applicant);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/applicants")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<Applicant>> getApplicantPages(@ParameterObject ApplicantCriteria criteria, @ParameterObject Pageable pageable) {
        Page<Applicant> page = queryApplicantService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/applicants/all")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<Applicant>> getAllApplicants(@ParameterObject ApplicantCriteria criteria) {
        List<Applicant> applicants = queryApplicantService.findListByCriteria(criteria);
        return ResponseEntity.ok(applicants);
    }

    @GetMapping("/applicants/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Applicant> getById(@PathVariable("id") Long id) {
        Applicant res = queryApplicantService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/applicants/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        applicantService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 