package com.app.homeworkoutapplication.module.document.service;

import com.app.homeworkoutapplication.module.document.dto.Document;
import com.app.homeworkoutapplication.module.document.service.criteria.DocumentCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryDocumentService {

    List<Document> findListByCriteria(DocumentCriteria criteria);

    Page<Document> findPageByCriteria(DocumentCriteria criteria, Pageable pageable);

    Document getById(Long id);

    Document getByName(String name);
}
