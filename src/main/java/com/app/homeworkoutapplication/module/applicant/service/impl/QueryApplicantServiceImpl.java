package com.app.homeworkoutapplication.module.applicant.service.impl;

import com.app.homeworkoutapplication.entity.*;
import com.app.homeworkoutapplication.entity.mapper.ApplicantMapper;
import com.app.homeworkoutapplication.entity.mapper.ArticleMapper;
import com.app.homeworkoutapplication.entity.mapper.DocumentMapper;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicant.service.QueryApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.criteria.ApplicantCriteria;
import com.app.homeworkoutapplication.module.article.service.QueryArticleService;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.module.document.dto.Document;
import com.app.homeworkoutapplication.module.document.service.QueryDocumentService;
import com.app.homeworkoutapplication.repository.ApplicantRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryApplicantServiceImpl extends QueryService<ApplicantEntity> implements QueryApplicantService {

    private final ApplicantRepository applicantRepository;

    private final QueryDocumentService queryDocumentService;

    private final QueryArticleService queryArticleService;

    private final ApplicantMapper applicantMapper;

    private final DocumentMapper documentMapper;

    private final ArticleMapper articleMapper;

    private final BlobStorageService blobStorageService;

    public QueryApplicantServiceImpl(ApplicantRepository applicantRepository, QueryDocumentService queryDocumentService, QueryArticleService queryArticleService, ApplicantMapper applicantMapper, DocumentMapper documentMapper, ArticleMapper articleMapper, BlobStorageService blobStorageService) {
        this.applicantRepository = applicantRepository;
        this.queryDocumentService = queryDocumentService;
        this.queryArticleService = queryArticleService;
        this.applicantMapper = applicantMapper;
        this.documentMapper = documentMapper;
        this.articleMapper = articleMapper;
        this.blobStorageService = blobStorageService;
    }

    @Override
    public List<Applicant> findListByCriteria(ApplicantCriteria criteria) {
        return applicantRepository.findAll(createSpecification(criteria)).stream()
                .map(entity -> {
                    Applicant applicant = applicantMapper.toDto(entity);
                    fetchData(entity, applicant);
                    return applicant;
                })
                .toList();
    }

    @Override
    public Page<Applicant> findPageByCriteria(ApplicantCriteria criteria, Pageable pageable) {
        Page<ApplicantEntity> page = applicantRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(entity -> {
                    Applicant applicant = applicantMapper.toDto(entity);
                    fetchData(entity, applicant);
                    return applicant;
                }).toList(),
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
        Applicant applicant = applicantMapper.toDto(applicantEntity.get());
        fetchData(applicantEntity.get(), applicant);
        return applicant;
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
        if (criteria.getUserId() != null) {
            specification = specification.and(findByUserId(criteria.getUserId()));
        }

        return specification;
    }

    private Specification<ApplicantEntity> findByUserId(LongFilter id) {
        if (id.getEquals() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ApplicantEntity, UserEntity> join = root.join(ApplicantEntity_.user, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(UserEntity_.id), id.getEquals());
            };
        }
        if (id.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ApplicantEntity, UserEntity> aiModelEntityJoin = root.join(
                        ApplicantEntity_.user,
                        JoinType.LEFT
                );
                return aiModelEntityJoin.get(UserEntity_.id).in(id.getIn());
            };
        }
        return null;
    }

    private void fetchData(ApplicantEntity entity, Applicant applicant) {
        List<Document> documents = queryDocumentService.findByApplicantId(entity.getId()).stream().map(document -> {
            if(document.getFilePath() != null) {
                document.setFileUrl(blobStorageService.getUrl(document.getFilePath()));
            }
            return document;
        }).toList();
        if(applicant.getArticleId() != null) {
            applicant.setArticle(queryArticleService.getById(applicant.getArticleId()));
        }
        applicant.setDocuments(documents);
    }
} 