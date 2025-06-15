package com.app.homeworkoutapplication.module.company.service;

import com.app.homeworkoutapplication.module.company.dto.Company;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {

    Company create(Company company);

    void updateImage(Long id, MultipartFile file);

    void enable(Long id);

    void disable(Long id);

    Company update(Company company);

    void delete(Long id);
}
