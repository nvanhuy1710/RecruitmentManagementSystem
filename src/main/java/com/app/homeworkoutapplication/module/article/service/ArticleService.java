package com.app.homeworkoutapplication.module.article.service;

import com.app.homeworkoutapplication.module.article.dto.Article;

public interface ArticleService {

    Article create(Article article);

    Article update(Article article);

    Article approve(Long id);

    Article reject(Long id);

    void delete(Long id);
}
