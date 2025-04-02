package com.app.homeworkoutapplication.module.blobstorage.service;

import org.springframework.web.multipart.MultipartFile;

public interface BlobStorageService {

    void save(MultipartFile multipartFile, String filePath);

    void copy(String sourcePath, String destinationPath);

    void delete(String filePath);

    String getUrl(String filePath);
}
