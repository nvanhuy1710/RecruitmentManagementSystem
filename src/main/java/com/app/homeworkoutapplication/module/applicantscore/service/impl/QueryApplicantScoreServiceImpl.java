package com.app.homeworkoutapplication.module.applicantscore.service.impl;

import com.app.homeworkoutapplication.entity.*;
import com.app.homeworkoutapplication.entity.mapper.ApplicantScoreMapper;
import com.app.homeworkoutapplication.module.applicantscore.dto.ApplicantScore;
import com.app.homeworkoutapplication.module.applicantscore.service.QueryApplicantScoreService;
import com.app.homeworkoutapplication.module.applicantscore.service.criteria.ApplicantScoreCriteria;
import com.app.homeworkoutapplication.repository.ApplicantScoreRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;

import java.util.List;
import java.util.Optional;

@Service
public class QueryApplicantScoreServiceImpl extends QueryService<ApplicantScoreEntity> implements QueryApplicantScoreService {

    private final ApplicantScoreRepository applicantScoreRepository;

    private final ApplicantScoreMapper applicantScoreMapper;

    public QueryApplicantScoreServiceImpl(ApplicantScoreRepository applicantScoreRepository, ApplicantScoreMapper applicantScoreMapper) {
        this.applicantScoreRepository = applicantScoreRepository;
        this.applicantScoreMapper = applicantScoreMapper;
    }

    @Override
    public List<ApplicantScore> findListByCriteria(ApplicantScoreCriteria criteria) {
        return applicantScoreRepository.findAll(createSpecification(criteria)).stream()
                .map(entity -> {
                    ApplicantScore applicant = applicantScoreMapper.toDto(entity);
                    fetchData(entity, applicant);
                    return applicant;
                })
                .toList();
    }

    @Override
    public List<ApplicantScore> findListByApplicantId(Long applicantId) {
        ApplicantScoreCriteria criteria = new ApplicantScoreCriteria();

        LongFilter filter = new LongFilter();
        filter.setEquals(applicantId);
        criteria.setApplicantId(filter);

        return findListByCriteria(criteria);
    }

    @Override
    public Page<ApplicantScore> findPageByCriteria(ApplicantScoreCriteria criteria, Pageable pageable) {
        Page<ApplicantScoreEntity> page = applicantScoreRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(entity -> {
                    ApplicantScore applicant = applicantScoreMapper.toDto(entity);
                    fetchData(entity, applicant);
                    return applicant;
                }).toList(),
                pageable,
                page.getTotalElements()
        );    }

    @Override
    public ApplicantScore getById(Long id) {
        Optional<ApplicantScoreEntity> applicantEntity = applicantScoreRepository.findById(id);
        if (applicantEntity.isEmpty()) {
            throw new NotFoundException("Not found applicant by id " + id);
        }
        ApplicantScore applicant = applicantScoreMapper.toDto(applicantEntity.get());
        fetchData(applicantEntity.get(), applicant);
        return applicant;
    }

    @Override
    public Optional<ApplicantScore> findById(Long id) {
        Optional<ApplicantScoreEntity> applicantEntity = applicantScoreRepository.findById(id);
        if (applicantEntity.isEmpty()) {
            return Optional.empty();
        }
        ApplicantScore applicant = applicantScoreMapper.toDto(applicantEntity.get());
        fetchData(applicantEntity.get(), applicant);
        return Optional.of(applicant);
    }

    private Specification<ApplicantScoreEntity> createSpecification(ApplicantScoreCriteria criteria) {
        Specification<ApplicantScoreEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), ApplicantScoreEntity_.id));
        }
        if (criteria.getOverall() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getOverall(), ApplicantScoreEntity_.overall));
        }
        if (criteria.getApplicantId() != null) {
            specification = specification.and(findByApplicantId(criteria.getApplicantId()));
        }

        return specification;
    }

    private Specification<ApplicantScoreEntity> findByApplicantId(LongFilter id) {
        if (id.getEquals() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ApplicantScoreEntity, ApplicantEntity> join = root.join(ApplicantScoreEntity_.applicant, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(ApplicantEntity_.id), id.getEquals());
            };
        }
        if (id.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ApplicantScoreEntity, ApplicantEntity> join = root.join(ApplicantScoreEntity_.applicant, JoinType.LEFT);
                return join.get(ApplicantEntity_.id).in(id.getIn());
            };
        }
        return null;
    }

    private void fetchData(ApplicantScoreEntity entity, ApplicantScore applicantScore) {}
}
