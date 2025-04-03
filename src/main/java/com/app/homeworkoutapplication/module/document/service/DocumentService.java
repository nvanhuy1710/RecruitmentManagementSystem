package com.app.homeworkoutapplication.module.document.service;

import com.app.homeworkoutapplication.module.document.dto.Document;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

    Document create(Document document, MultipartFile file);

    void delete(Long id);
}
