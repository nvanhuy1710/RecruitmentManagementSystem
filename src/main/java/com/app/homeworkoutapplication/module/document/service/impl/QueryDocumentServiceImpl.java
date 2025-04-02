package com.app.homeworkoutapplication.module.document.service.impl;

import com.app.homeworkoutapplication.entity.DocumentEntity;
import com.app.homeworkoutapplication.entity.DocumentEntity_;
import com.app.homeworkoutapplication.entity.mapper.DocumentMapper;
import com.app.homeworkoutapplication.module.document.dto.Document;
import com.app.homeworkoutapplication.module.document.service.QueryDocumentService;
import com.app.homeworkoutapplication.module.document.service.criteria.DocumentCriteria;
import com.app.homeworkoutapplication.repository.DocumentRepository;
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
public class QueryDocumentServiceImpl extends QueryService<DocumentEntity> implements QueryDocumentService {

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;


    public QueryDocumentServiceImpl(DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    public List<Document> findListByCriteria(DocumentCriteria criteria) {
        return documentRepository.findAll(createSpecification(criteria)).stream().map(documentMapper::toDto).toList();
    }

    public Page<Document> findPageByCriteria(DocumentCriteria criteria, Pageable pageable) {
        Page<DocumentEntity> page =  documentRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(documentMapper::toDto).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    public Document getById(Long id) {
        Optional<DocumentEntity> documentEntity = documentRepository.findById(id);
        if (documentEntity.isEmpty()) {
            throw new NotFoundException("Not found document by id " + id);
        }
        return documentMapper.toDto(documentEntity.get());
    }

    public Document getByName(String name) {
        Optional<DocumentEntity> documentEntity = documentRepository.findByName(name);
        if (documentEntity.isEmpty()) {
            throw new NotFoundException("Not found document by name " + name);
        }
        return documentMapper.toDto(documentEntity.get());
    }


    private Specification<DocumentEntity> createSpecification(DocumentCriteria criteria) {
        Specification<DocumentEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), DocumentEntity_.id));
        }
        if (criteria.getName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getName(), DocumentEntity_.name));
        }

        return specification;
    }
}
