package com.app.homeworkoutapplication.module.document.service.impl;

import com.app.homeworkoutapplication.entity.ApplicantEntity;
import com.app.homeworkoutapplication.entity.ApplicantEntity_;
import com.app.homeworkoutapplication.entity.DocumentEntity;
import com.app.homeworkoutapplication.entity.DocumentEntity_;
import com.app.homeworkoutapplication.entity.mapper.DocumentMapper;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.module.document.dto.Document;
import com.app.homeworkoutapplication.module.document.service.QueryDocumentService;
import com.app.homeworkoutapplication.module.document.service.criteria.DocumentCriteria;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.repository.DocumentRepository;
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
public class QueryDocumentServiceImpl extends QueryService<DocumentEntity> implements QueryDocumentService {

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;

    private final BlobStorageService blobStorageService;

    public QueryDocumentServiceImpl(DocumentRepository documentRepository, DocumentMapper documentMapper, BlobStorageService blobStorageService) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
        this.blobStorageService = blobStorageService;
    }

    public List<Document> findListByCriteria(DocumentCriteria criteria) {
        return documentRepository.findAll(createSpecification(criteria)).stream().map(
                entity -> {
                    Document document = documentMapper.toDto(entity);
                    fetchData(entity, document);
                    return document;
                }
        ).toList();
    }

    public Page<Document> findPageByCriteria(DocumentCriteria criteria, Pageable pageable) {
        Page<DocumentEntity> page =  documentRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(entity -> {
                    Document document = documentMapper.toDto(entity);
                    fetchData(entity, document);
                    return document;
                }).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public List<Document> findByApplicantId(Long id) {
        DocumentCriteria criteria = new DocumentCriteria();
        LongFilter idFilter = new LongFilter();
        idFilter.setEquals(id);
        criteria.setApplicantId(idFilter);
        return findListByCriteria(criteria);
    }

    public Document getById(Long id) {
        Optional<DocumentEntity> documentEntity = documentRepository.findById(id);
        if (documentEntity.isEmpty()) {
            throw new NotFoundException("Not found document by id " + id);
        }
        Document document = documentMapper.toDto(documentEntity.get());
        fetchData(documentEntity.get(), document);
        return document;
    }

    public Document getByName(String name) {
        Optional<DocumentEntity> documentEntity = documentRepository.findByName(name);
        if (documentEntity.isEmpty()) {
            throw new NotFoundException("Not found document by name " + name);
        }
        Document document = documentMapper.toDto(documentEntity.get());
        fetchData(documentEntity.get(), document);
        return document;
    }


    private Specification<DocumentEntity> createSpecification(DocumentCriteria criteria) {
        Specification<DocumentEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), DocumentEntity_.id));
        }
        if (criteria.getName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getName(), DocumentEntity_.name));
        }
        if (criteria.getApplicantId() != null) {
            specification = specification.and(findByApplicantId(criteria.getApplicantId()));

        }

        return specification;
    }

    private Specification<DocumentEntity> findByApplicantId(LongFilter id) {
        if (id.getEquals() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<DocumentEntity, ApplicantEntity> join = root.join(DocumentEntity_.applicant, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(ApplicantEntity_.id), id.getEquals());
            };
        }
        if (id.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<DocumentEntity, ApplicantEntity> aiModelEntityJoin = root.join(
                        DocumentEntity_.applicant,
                        JoinType.LEFT
                );
                return aiModelEntityJoin.get(ApplicantEntity_.id).in(id.getIn());
            };
        }
        return null;
    }

    private void fetchData(DocumentEntity entity, Document document) {
        if(entity.getFilePath() != null) {
            document.setFileUrl(blobStorageService.getUrl(entity.getFilePath()));
        }
    }
}
