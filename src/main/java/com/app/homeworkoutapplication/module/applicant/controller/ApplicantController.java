package com.app.homeworkoutapplication.module.applicant.controller;

import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicant.service.ApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.CaculateApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.QueryApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.criteria.ApplicantCriteria;
import com.app.homeworkoutapplication.security.AuthorityConstant;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
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
import tech.jhipster.service.filter.LongFilter;
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
    private final CaculateApplicantService caculateApplicantService;
    private final CurrentUserUtil currentUserUtil;

    public ApplicantController(ApplicantService applicantService, QueryApplicantService queryApplicantService, CaculateApplicantService caculateApplicantService, CurrentUserUtil currentUserUtil) {
        this.applicantService = applicantService;
        this.queryApplicantService = queryApplicantService;
        this.caculateApplicantService = caculateApplicantService;
        this.currentUserUtil = currentUserUtil;
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
    @PreAuthorize("hasAnyAuthority('" + AuthorityConstant.EMPLOYER + "', '" + AuthorityConstant.ADMIN + "')")
    public ResponseEntity<List<Applicant>> getApplicantPages(@ParameterObject ApplicantCriteria criteria, @ParameterObject Pageable pageable) {
        Page<Applicant> page = queryApplicantService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/applicants/{articleId}/match-score")
//    @PreAuthorize("hasAnyAuthority('" + AuthorityConstant.EMPLOYER + "', '" + AuthorityConstant.ADMIN + "')")
    public ResponseEntity<Void> caculateMatchScore(@PathVariable Long articleId) {
        caculateApplicantService.caculateMatchScore(articleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-applicants")
    public ResponseEntity<List<Applicant>> getMyApplicantPages(@ParameterObject ApplicantCriteria criteria, @ParameterObject Pageable pageable) {
        criteria.setUserId(setCurrentUser());
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

    @PutMapping("/applicants/{id}/accept")
    @PreAuthorize("hasAnyAuthority('" + AuthorityConstant.EMPLOYER + "', '" + AuthorityConstant.ADMIN + "')")
    public ResponseEntity<Void> acceptApplicant(@PathVariable Long id) {
        applicantService.accept(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/applicants/{id}/decline")
    @PreAuthorize("hasAnyAuthority('" + AuthorityConstant.EMPLOYER + "', '" + AuthorityConstant.ADMIN + "')")
    public ResponseEntity<List<Applicant>> declineApplicant(@PathVariable Long id) {
        applicantService.decline(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/applicants/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        applicantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private LongFilter setCurrentUser() {
        LongFilter id = new LongFilter();
        id.setEquals(currentUserUtil.getCurrentUser().getId());
        return id;
    }
} 