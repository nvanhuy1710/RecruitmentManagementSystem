package com.app.homeworkoutapplication.module.company.service.impl;

import com.app.homeworkoutapplication.entity.CompanyEntity;
import com.app.homeworkoutapplication.entity.CompanyEntity_;
import com.app.homeworkoutapplication.entity.UserEntity;
import com.app.homeworkoutapplication.entity.UserSkillEntity;
import com.app.homeworkoutapplication.entity.mapper.CompanyMapper;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.module.company.dto.Company;
import com.app.homeworkoutapplication.module.company.service.QueryCompanyService;
import com.app.homeworkoutapplication.module.company.service.criteria.CompanyCriteria;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.repository.CompanyRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryCompanyServiceImpl extends QueryService<CompanyEntity> implements QueryCompanyService {

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    private final BlobStorageService blobStorageService;


    public QueryCompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper, BlobStorageService blobStorageService) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
        this.blobStorageService = blobStorageService;
    }

    public List<Company> findListByCriteria(CompanyCriteria criteria) {
        return companyRepository.findAll(createSpecification(criteria)).stream().map(
                companyEntity -> {
                    Company company = companyMapper.toDto(companyEntity);
                    fetchData(companyEntity, company);
                    return company;
                }
        ).toList();
    }

    public Page<Company> findPageByCriteria(CompanyCriteria criteria, Pageable pageable) {
        Page<CompanyEntity> page =  companyRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(companyEntity -> {
                    Company company = companyMapper.toDto(companyEntity);
                    fetchData(companyEntity, company);
                    return company;
                }).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    public Company getById(Long id) {
        Optional<CompanyEntity> companyEntity = companyRepository.findById(id);
        if (companyEntity.isEmpty()) {
            throw new NotFoundException("Not found company by id " + id);
        }
        Company company = companyMapper.toDto(companyEntity.get());
        fetchData(companyEntity.get(), company);
        return company;
    }

    public Company getByName(String name) {
        Optional<CompanyEntity> companyEntity = companyRepository.findByName(name);
        if (companyEntity.isEmpty()) {
            throw new NotFoundException("Not found company by name " + name);
        }
        Company company = companyMapper.toDto(companyEntity.get());
        fetchData(companyEntity.get(), company);
        return company;
    }

    private Specification<CompanyEntity> createSpecification(CompanyCriteria criteria) {
        Specification<CompanyEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), CompanyEntity_.id));
        }
        if (criteria.getName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getName(), CompanyEntity_.name));
        }
        if (criteria.getAddress() != null) {
            specification = specification.and(buildStringSpecification(criteria.getAddress(), CompanyEntity_.address));
        }
        if (criteria.getLocation() != null) {
            specification = specification.and(buildStringSpecification(criteria.getLocation(), CompanyEntity_.location));
        }
        return specification;
    }

    private void fetchData(CompanyEntity entity, Company company) {
        if(entity.getImagePath() != null) {
            company.setImageUrl(blobStorageService.getUrl(entity.getImagePath()));
        }
    }
}
