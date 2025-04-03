package com.app.homeworkoutapplication.module.document.service.impl;

import com.app.homeworkoutapplication.entity.mapper.DocumentMapper;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.module.document.dto.Document;
import com.app.homeworkoutapplication.module.document.service.DocumentService;
import com.app.homeworkoutapplication.module.document.service.QueryDocumentService;
import com.app.homeworkoutapplication.repository.DocumentRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

    private String EXERCISE_PATH = "homeworkoutapplication/exercise/{id}/";

    private final DocumentRepository documentRepository;

    private final QueryDocumentService queryDocumentService;

    private final DocumentMapper documentMapper;

    private final BlobStorageService blobStorageService;

    public DocumentServiceImpl(DocumentRepository documentRepository, QueryDocumentService queryDocumentService, DocumentMapper documentMapper, BlobStorageService blobStorageService) {
        this.documentRepository = documentRepository;
        this.queryDocumentService = queryDocumentService;
        this.documentMapper = documentMapper;
        this.blobStorageService = blobStorageService;
    }

    public Document create(Document document, MultipartFile file) {
        if (document.getId() != null) {
            throw new BadRequestException("id not null!");
        }

        String fileName = file.getOriginalFilename();

        Map<String, String> values = new HashMap<>();
        values.put("id", UUID.randomUUID().toString());
        String filePath = StringSubstitutor.replace(EXERCISE_PATH, values, "{", "}") + fileName;
        blobStorageService.save(file, filePath);
        document.setFilePath(filePath);

        return documentMapper.toDto(documentRepository.save(documentMapper.toEntity(document)));
    }

    public void delete(Long id) {

        Document document = queryDocumentService.getById(id);

        if(document.getFilePath() != null) {
            try {
                blobStorageService.delete(document.getFilePath());
            } catch (Exception e) {
                logger.error("Failed to delete file in document: {}", document.getId());
            }
        }

        documentRepository.deleteById(id);
    }
}
