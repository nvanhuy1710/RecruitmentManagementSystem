package com.app.homeworkoutapplication.module.company.service.impl;

import com.app.homeworkoutapplication.entity.enumeration.CompanyStatus;
import com.app.homeworkoutapplication.entity.mapper.CompanyMapper;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.module.company.dto.Company;
import com.app.homeworkoutapplication.module.company.service.CompanyService;
import com.app.homeworkoutapplication.module.company.service.QueryCompanyService;
import com.app.homeworkoutapplication.repository.CompanyRepository;
import com.app.homeworkoutapplication.util.BlobStoragePathUtil;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    private final QueryCompanyService queryCompanyService;

    private final BlobStorageService blobStorageService;

    private final BlobStoragePathUtil blobStoragePathUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper, QueryCompanyService queryCompanyService, BlobStorageService blobStorageService, BlobStoragePathUtil blobStoragePathUtil) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.queryCompanyService = queryCompanyService;
        this.blobStorageService = blobStorageService;
        this.blobStoragePathUtil = blobStoragePathUtil;
    }

    public Company create(Company company) {
        if (company.getId() != null) {
            throw new BadRequestException("id not null!");
        }
        return companyMapper.toDto(companyRepository.save(companyMapper.toEntity(company)));
    }

    @Override
    public void updateImage(Long id, MultipartFile file) {
        Company company = queryCompanyService.getById(id);
        String path = blobStoragePathUtil.getCompanyImagePath(company.getId().toString(), file.getOriginalFilename());
        blobStorageService.save(file, path);
        company.setImagePath(path);
        companyRepository.save(companyMapper.toEntity(company));
    }

    @Override
    public void enable(Long id) {
        Company company = queryCompanyService.getById(id);
        company.setStatus(CompanyStatus.ENABLED);
        companyRepository.save(companyMapper.toEntity(company));
    }

    @Override
    public void disable(Long id) {
        Company company = queryCompanyService.getById(id);
        company.setStatus(CompanyStatus.DISABLED);
        companyRepository.save(companyMapper.toEntity(company));
    }

    public Company update(Company company) {
        if (company.getId() == null) {
            throw new BadRequestException("id null!");
        }
        return companyMapper.toDto(companyRepository.save(companyMapper.toEntity(company)));
    }

    public void delete(Long id) {
        companyRepository.deleteById(id);
    }
}
