package com.app.homeworkoutapplication.module.document.service.impl;

import com.app.homeworkoutapplication.entity.mapper.DocumentMapper;
import com.app.homeworkoutapplication.module.document.dto.Document;
import com.app.homeworkoutapplication.module.document.service.DocumentService;
import com.app.homeworkoutapplication.repository.DocumentRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;

    public DocumentServiceImpl(DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    public Document create(Document document) {
        if (document.getId() != null) {
            throw new BadRequestException("id not null!");
        }
        return documentMapper.toDto(documentRepository.save(documentMapper.toEntity(document)));
    }

    public Document update(Document document) {
        if (document.getId() == null) {
            throw new BadRequestException("id null!");
        }
        return documentMapper.toDto(documentRepository.save(documentMapper.toEntity(document)));
    }

    public void delete(Long id) {
        documentRepository.deleteById(id);
    }
}
