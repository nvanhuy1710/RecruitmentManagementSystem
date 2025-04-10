package com.app.homeworkoutapplication.module.joblevel.controller;

import com.app.homeworkoutapplication.module.joblevel.dto.JobLevel;
import com.app.homeworkoutapplication.module.joblevel.service.QueryJobLevelService;
import com.app.homeworkoutapplication.module.joblevel.service.JobLevelService;
import com.app.homeworkoutapplication.module.joblevel.service.criteria.JobLevelCriteria;
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
public class JobLevelController {

    private final JobLevelService jobLevelService;

    private final QueryJobLevelService queryJobLevelService;

    public JobLevelController(JobLevelService jobLevelService, QueryJobLevelService queryJobLevelService) {
        this.jobLevelService = jobLevelService;
        this.queryJobLevelService = queryJobLevelService;
    }

    @PostMapping("/job-levels")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<JobLevel> create(@Valid @RequestBody JobLevel job_level) throws URISyntaxException {
        JobLevel result = jobLevelService.create(job_level);
        return ResponseEntity.created(new URI("/api/job-levels/" + result.getId())).body(result);
    }

    @PutMapping("/job-levels/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<JobLevel> update(@PathVariable("id") Long id, @Valid @RequestBody JobLevel job_level){
        if (job_level.getId() == null) job_level.setId(id);
        JobLevel res = jobLevelService.update(job_level);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/job-levels")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<JobLevel>> getJobLevelPages(@ParameterObject JobLevelCriteria criteria, @ParameterObject Pageable pageable) {
        Page<JobLevel> page = queryJobLevelService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/job-levels/all")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<JobLevel>> getAllJobLevels(@ParameterObject JobLevelCriteria criteria){
        List<JobLevel> jobLevels = queryJobLevelService.findListByCriteria(criteria);
        return ResponseEntity.ok(jobLevels);
    }

    @GetMapping("/job-levels/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<JobLevel> getById(@PathVariable("id") Long id){
        JobLevel res = queryJobLevelService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/job-levels/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        jobLevelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
