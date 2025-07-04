package com.app.homeworkoutapplication.module.article.service;

import com.app.homeworkoutapplication.module.article.dto.Article;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {

    Article create(Article article, MultipartFile file);

    void updateImage(Long id, MultipartFile file);

    Article update(Article article);

    Article close(Long id);

    Article approve(Long id);

    Article reject(Long id);

    void delete(Long id);
}
