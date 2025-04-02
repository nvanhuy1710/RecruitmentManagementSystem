package com.app.homeworkoutapplication.module.applicant.service.impl;

import com.app.homeworkoutapplication.entity.ApplicantEntity;
import com.app.homeworkoutapplication.entity.ApplicantEntity_;
import com.app.homeworkoutapplication.entity.mapper.ApplicantMapper;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicant.service.QueryApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.criteria.ApplicantCriteria;
import com.app.homeworkoutapplication.repository.ApplicantRepository;
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
public class QueryApplicantServiceImpl extends QueryService<ApplicantEntity> implements QueryApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantMapper applicantMapper;

    public QueryApplicantServiceImpl(ApplicantRepository applicantRepository, ApplicantMapper applicantMapper) {
        this.applicantRepository = applicantRepository;
        this.applicantMapper = applicantMapper;
    }

    @Override
    public List<Applicant> findListByCriteria(ApplicantCriteria criteria) {
        return applicantRepository.findAll(createSpecification(criteria)).stream()
                .map(applicantMapper::toDto)
                .toList();
    }

    @Override
    public Page<Applicant> findPageByCriteria(ApplicantCriteria criteria, Pageable pageable) {
        Page<ApplicantEntity> page = applicantRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(applicantMapper::toDto).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public Applicant getById(Long id) {
        Optional<ApplicantEntity> applicantEntity = applicantRepository.findById(id);
        if (applicantEntity.isEmpty()) {
            throw new NotFoundException("Not found applicant by id " + id);
        }
        return applicantMapper.toDto(applicantEntity.get());
    }

    private Specification<ApplicantEntity> createSpecification(ApplicantCriteria criteria) {
        Specification<ApplicantEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicantEntity_.id));
        }
        if (criteria.getFullName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getFullName(), ApplicantEntity_.fullName));
        }
        if (criteria.getPhone() != null) {
            specification = specification.and(buildStringSpecification(criteria.getPhone(), ApplicantEntity_.phone));
        }
        if (criteria.getStatus() != null) {
            specification = specification.and(buildSpecification(criteria.getStatus(), ApplicantEntity_.status));
        }

        return specification;
    }
} 