package com.app.homeworkoutapplication.module.company.service;

import com.app.homeworkoutapplication.module.company.dto.Company;
import com.app.homeworkoutapplication.module.company.service.criteria.CompanyCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryCompanyService {

    List<Company> findListByCriteria(CompanyCriteria criteria);

    Page<Company> findPageByCriteria(CompanyCriteria criteria, Pageable pageable);

    Company getById(Long id);

    Company getByName(String name);
}
