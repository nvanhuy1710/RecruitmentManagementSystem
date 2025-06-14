package com.app.homeworkoutapplication.module.company.service;

import com.app.homeworkoutapplication.module.company.dto.Company;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {

    Company create(Company company);

    void updateImage(Long id, MultipartFile file);

    Company update(Company company);

    void delete(Long id);
}
