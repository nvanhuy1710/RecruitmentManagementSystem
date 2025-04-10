package com.app.homeworkoutapplication.module.article.controller;

import com.app.homeworkoutapplication.entity.enumeration.ArticleStatus;
import com.app.homeworkoutapplication.entity.filter.ArticleStatusFilter;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.service.ArticleService;
import com.app.homeworkoutapplication.module.article.service.QueryArticleService;
import com.app.homeworkoutapplication.module.article.service.criteria.ArticleCriteria;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.web.util.PaginationUtil;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/public/api")
@SecurityRequirement(name = "Authentication")
public class PublicArticleController {

    private final ArticleService articleService;

    private final QueryArticleService queryArticleService;

    public PublicArticleController(ArticleService articleService, QueryArticleService queryArticleService) {
        this.articleService = articleService;
        this.queryArticleService = queryArticleService;
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getArticlePages(@ParameterObject ArticleCriteria criteria, @ParameterObject Pageable pageable) {

        ArticleStatusFilter statusFilter = new ArticleStatusFilter();
        statusFilter.setEquals(ArticleStatus.APPROVED);
        criteria.setStatus(statusFilter);

        InstantFilter instantFilter = new InstantFilter();
        instantFilter.setGreaterThan(Instant.now());
        criteria.setDueDate(instantFilter);

        Page<Article> page = queryArticleService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/articles/all")
    public ResponseEntity<List<Article>> getAllArticles(@ParameterObject ArticleCriteria criteria){

        ArticleStatusFilter statusFilter = new ArticleStatusFilter();
        statusFilter.setEquals(ArticleStatus.APPROVED);
        criteria.setStatus(statusFilter);

        InstantFilter instantFilter = new InstantFilter();
        instantFilter.setGreaterThan(Instant.now());
        criteria.setDueDate(instantFilter);

        List<Article> articles = queryArticleService.findListByCriteria(criteria);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> getById(@PathVariable("id") Long id){
        Article res = queryArticleService.getById(id);
        return ResponseEntity.ok(res);
    }
}