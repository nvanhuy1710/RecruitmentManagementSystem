package com.app.homeworkoutapplication.module.company.controller;

import com.app.homeworkoutapplication.module.company.dto.Company;
import com.app.homeworkoutapplication.module.company.service.QueryCompanyService;
import com.app.homeworkoutapplication.module.company.service.CompanyService;
import com.app.homeworkoutapplication.module.company.service.criteria.CompanyCriteria;
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
public class CompanyController {

    private final CompanyService companyService;

    private final QueryCompanyService queryCompanyService;

    public CompanyController(CompanyService companyService, QueryCompanyService queryCompanyService) {
        this.companyService = companyService;
        this.queryCompanyService = queryCompanyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> create(@Valid @RequestBody Company company) throws URISyntaxException {
        Company result = companyService.create(company);
        return ResponseEntity.created(new URI("/api/companies/" + result.getId())).body(result);
    }

    @PutMapping("/companies/{id}/update-image")
    public ResponseEntity<Void> create(@PathVariable Long id,@RequestParam("file")  MultipartFile file) throws URISyntaxException {
        companyService.updateImage(id, file);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> update(@PathVariable("id") Long id, @Valid @RequestBody Company company){
        if (company.getId() == null) company.setId(id);
        Company res = companyService.update(company);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getCompanyPages(@ParameterObject CompanyCriteria criteria, @ParameterObject Pageable pageable) {
        Page<Company> page = queryCompanyService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/companies/all")
    public ResponseEntity<List<Company>> getAllCompanys(@ParameterObject CompanyCriteria criteria){
        List<Company> industries = queryCompanyService.findListByCriteria(criteria);
        return ResponseEntity.ok(industries);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getById(@PathVariable("id") Long id){
        Company res = queryCompanyService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
