package com.app.homeworkoutapplication.module.industry.service;

import com.app.homeworkoutapplication.module.industry.dto.Industry;
import com.app.homeworkoutapplication.module.industry.service.criteria.IndustryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryIndustryService {

    List<Industry> findListByCriteria(IndustryCriteria criteria);

    Page<Industry> findPageByCriteria(IndustryCriteria criteria, Pageable pageable);

    Industry getById(Long id);

    Industry getByName(String name);
}
