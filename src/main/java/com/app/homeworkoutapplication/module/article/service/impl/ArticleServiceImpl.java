package com.app.homeworkoutapplication.module.article.service.impl;

import com.app.homeworkoutapplication.entity.mapper.ArticleMapper;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.service.ArticleService;
import com.app.homeworkoutapplication.repository.ArticleRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    public Article create(Article article) {
        if (article.getId() != null) {
            throw new BadRequestException("id not null!");
        }
        return articleMapper.toDto(articleRepository.save(articleMapper.toEntity(article)));
    }

    public Article update(Article article) {
        if (article.getId() == null) {
            throw new BadRequestException("id null!");
        }
        return articleMapper.toDto(articleRepository.save(articleMapper.toEntity(article)));
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
