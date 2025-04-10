package com.app.homeworkoutapplication.module.article.service.impl;

import com.app.homeworkoutapplication.entity.enumeration.ArticleStatus;
import com.app.homeworkoutapplication.entity.mapper.ArticleMapper;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.service.ArticleService;
import com.app.homeworkoutapplication.module.article.service.QueryArticleService;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.repository.ArticleRepository;
import com.app.homeworkoutapplication.util.BlobStoragePathUtil;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final QueryArticleService queryArticleService;

    private final ArticleMapper articleMapper;

    private final BlobStorageService blobStorageService;

    private final BlobStoragePathUtil blobStoragePathUtil;

    private final CurrentUserUtil currentUserUtil;

    public ArticleServiceImpl(ArticleRepository articleRepository, QueryArticleService queryArticleService, ArticleMapper articleMapper, BlobStorageService blobStorageService, BlobStoragePathUtil blobStoragePathUtil, CurrentUserUtil currentUserUtil) {
        this.articleRepository = articleRepository;
        this.queryArticleService = queryArticleService;
        this.articleMapper = articleMapper;
        this.blobStorageService = blobStorageService;
        this.blobStoragePathUtil = blobStoragePathUtil;
        this.currentUserUtil = currentUserUtil;
    }

    public Article create(Article article, MultipartFile file) {
        if (article.getId() != null) {
            throw new BadRequestException("id not null!");
        }

        article.setUserId(currentUserUtil.getCurrentUser().getId());
        article.setStatus(ArticleStatus.PENDING);

        String path = blobStoragePathUtil.getArticlePath(UUID.randomUUID().toString(), file.getOriginalFilename());
        blobStorageService.save(file, path);
        article.setMainImagePath(path);

        return articleMapper.toDto(articleRepository.save(articleMapper.toEntity(article)));
    }

    @Override
    public void updateImage(Long id, MultipartFile file) {
        Article article = queryArticleService.getById(id);
        String path = blobStoragePathUtil.getArticlePath(UUID.randomUUID().toString(), file.getOriginalFilename());
        blobStorageService.save(file, path);
        article.setMainImagePath(path);

        articleRepository.save(articleMapper.toEntity(article));
    }

    public Article update(Article article) {
        if (article.getId() == null) {
            throw new BadRequestException("id null!");
        }

        Article exist = queryArticleService.getById(article.getId());
        article.setUserId(exist.getUserId());
        article.setMainImagePath(exist.getMainImagePath());
        article.setStatus(exist.getStatus());

        return articleMapper.toDto(articleRepository.save(articleMapper.toEntity(article)));
    }

    public Article approve(Long id) {
        Article article = queryArticleService.getById(id);
        article.setStatus(ArticleStatus.APPROVED);
        return articleMapper.toDto(articleRepository.save(articleMapper.toEntity(article)));
    }

    public Article reject(Long id) {
        Article article = queryArticleService.getById(id);
        article.setStatus(ArticleStatus.REJECTED);
        return articleMapper.toDto(articleRepository.save(articleMapper.toEntity(article)));
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
