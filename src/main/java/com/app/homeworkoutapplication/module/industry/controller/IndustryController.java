package com.app.homeworkoutapplication.module.industry.controller;

import com.app.homeworkoutapplication.module.industry.dto.Industry;
import com.app.homeworkoutapplication.module.industry.service.QueryIndustryService;
import com.app.homeworkoutapplication.module.industry.service.IndustryService;
import com.app.homeworkoutapplication.module.industry.service.criteria.IndustryCriteria;
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
public class IndustryController {

    private final IndustryService industryService;

    private final QueryIndustryService queryIndustryService;

    public IndustryController(IndustryService industryService, QueryIndustryService queryIndustryService) {
        this.industryService = industryService;
        this.queryIndustryService = queryIndustryService;
    }

    @PostMapping("/industries")
    public ResponseEntity<Industry> create(@Valid @RequestBody Industry industry) throws URISyntaxException {
        Industry result = industryService.create(industry);
        return ResponseEntity.created(new URI("/api/industries/" + result.getId())).body(result);
    }

    @PutMapping("/industries/{id}")
    public ResponseEntity<Industry> update(@PathVariable("id") Long id, @Valid @RequestBody Industry industry){
        if (industry.getId() == null) industry.setId(id);
        Industry res = industryService.update(industry);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/industries")
    public ResponseEntity<List<Industry>> getIndustryPages(@ParameterObject IndustryCriteria criteria, @ParameterObject Pageable pageable) {
        Page<Industry> page = queryIndustryService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/industries/all")
    public ResponseEntity<List<Industry>> getAllIndustrys(@ParameterObject IndustryCriteria criteria){
        List<Industry> industries = queryIndustryService.findListByCriteria(criteria);
        return ResponseEntity.ok(industries);
    }

    @GetMapping("/industries/{id}")
    public ResponseEntity<Industry> getById(@PathVariable("id") Long id){
        Industry res = queryIndustryService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/industries/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        industryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
