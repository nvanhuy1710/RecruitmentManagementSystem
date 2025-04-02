package com.app.homeworkoutapplication.module.article.service;

import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.service.criteria.ArticleCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryArticleService {

    List<Article> findListByCriteria(ArticleCriteria criteria);

    Page<Article> findPageByCriteria(ArticleCriteria criteria, Pageable pageable);

    Article getById(Long id);
}
