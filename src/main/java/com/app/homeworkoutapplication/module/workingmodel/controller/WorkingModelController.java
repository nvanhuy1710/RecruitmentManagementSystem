package com.app.homeworkoutapplication.module.workingmodel.controller;

import com.app.homeworkoutapplication.module.workingmodel.dto.WorkingModel;
import com.app.homeworkoutapplication.module.workingmodel.service.QueryWorkingModelService;
import com.app.homeworkoutapplication.module.workingmodel.service.WorkingModelService;
import com.app.homeworkoutapplication.module.workingmodel.service.criteria.WorkingModelCriteria;
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
public class WorkingModelController {

    private final WorkingModelService workingModelService;
    private final QueryWorkingModelService queryWorkingModelService;

    public WorkingModelController(WorkingModelService workingModelService, QueryWorkingModelService queryWorkingModelService) {
        this.workingModelService = workingModelService;
        this.queryWorkingModelService = queryWorkingModelService;
    }

    @PostMapping("/working-models")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<WorkingModel> create(@Valid @RequestBody WorkingModel workingModel) throws URISyntaxException {
        WorkingModel result = workingModelService.create(workingModel);
        return ResponseEntity.created(new URI("/api/working-models/" + result.getId())).body(result);
    }

    @PutMapping("/working-models/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<WorkingModel> update(@PathVariable("id") Long id, @Valid @RequestBody WorkingModel workingModel) {
        if (workingModel.getId() == null) workingModel.setId(id);
        WorkingModel res = workingModelService.update(workingModel);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/working-models")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<WorkingModel>> getWorkingModelPages(@ParameterObject WorkingModelCriteria criteria, @ParameterObject Pageable pageable) {
        Page<WorkingModel> page = queryWorkingModelService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/working-models/all")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<WorkingModel>> getAllWorkingModels(@ParameterObject WorkingModelCriteria criteria) {
        List<WorkingModel> workingModels = queryWorkingModelService.findListByCriteria(criteria);
        return ResponseEntity.ok(workingModels);
    }

    @GetMapping("/working-models/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<WorkingModel> getById(@PathVariable("id") Long id) {
        WorkingModel res = queryWorkingModelService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/working-models/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        workingModelService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 