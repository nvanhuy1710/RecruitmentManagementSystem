package com.app.homeworkoutapplication.module.company.controller;

import com.app.homeworkoutapplication.entity.enumeration.CompanyStatus;
import com.app.homeworkoutapplication.entity.filter.CompanyStatusFilter;
import com.app.homeworkoutapplication.module.company.dto.Company;
import com.app.homeworkoutapplication.module.company.service.QueryCompanyService;
import com.app.homeworkoutapplication.module.company.service.criteria.CompanyCriteria;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/public/api")
public class PublicCompanyController {

    private final QueryCompanyService queryCompanyService;

    private final CurrentUserUtil currentUserUtil;

    public PublicCompanyController(QueryCompanyService queryCompanyService, CurrentUserUtil currentUserUtil) {
        this.queryCompanyService = queryCompanyService;
        this.currentUserUtil = currentUserUtil;
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getCompanyPages(@ParameterObject CompanyCriteria criteria, @ParameterObject Pageable pageable) {
        filterByRole(criteria);
        Page<Company> page = queryCompanyService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/companies/all")
    public ResponseEntity<List<Company>> getAllCompanies(@ParameterObject CompanyCriteria criteria){
        filterByRole(criteria);
        List<Company> industries = queryCompanyService.findListByCriteria(criteria);
        return ResponseEntity.ok(industries);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getById(@PathVariable("id") Long id){
        Company res = queryCompanyService.getById(id);
        return ResponseEntity.ok(res);
    }

    private void filterByRole(CompanyCriteria criteria) {
        if(currentUserUtil.getCurrentUser() == null || !Objects.equals(currentUserUtil.getCurrentUser().getRoleName(), "ADMIN")) {
            CompanyStatusFilter filter = new CompanyStatusFilter();
            filter.setEquals(CompanyStatus.ENABLED);
            criteria.setStatus(filter);
        }
    }
}
