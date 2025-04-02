package com.app.homeworkoutapplication.module.document.service;

import com.app.homeworkoutapplication.module.document.dto.Document;

public interface DocumentService {

    Document create(Document document);

    Document update(Document document);

    void delete(Long id);
}
