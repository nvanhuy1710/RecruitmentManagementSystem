package com.app.homeworkoutapplication.module.article.controller;

import com.app.homeworkoutapplication.entity.enumeration.ArticleStatus;
import com.app.homeworkoutapplication.entity.filter.ArticleStatusFilter;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.service.QueryArticleService;
import com.app.homeworkoutapplication.module.article.service.ArticleService;
import com.app.homeworkoutapplication.module.article.service.criteria.ArticleCriteria;
import com.app.homeworkoutapplication.security.AuthorityConstant;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Authentication")
public class AdminArticleController {

    private final ArticleService articleService;

    private final QueryArticleService queryArticleService;

    private final CurrentUserUtil currentUserUtil;

    public AdminArticleController(ArticleService articleService, QueryArticleService queryArticleService, CurrentUserUtil currentUserUtil) {
        this.articleService = articleService;
        this.queryArticleService = queryArticleService;
        this.currentUserUtil = currentUserUtil;
    }

    @GetMapping("/admin-articles/pending-approve")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<Article>> getPendingArticlePages(@ParameterObject ArticleCriteria criteria, @ParameterObject Pageable pageable) {

        ArticleStatusFilter statusFilter = new ArticleStatusFilter();
        statusFilter.setEquals(ArticleStatus.PENDING);
        criteria.setStatus(statusFilter);

        Page<Article> page = queryArticleService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/admin-articles")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<Article>> getArticlePages(@ParameterObject ArticleCriteria criteria, @ParameterObject Pageable pageable) {
        Page<Article> page = queryArticleService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/admin-articles/all")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<List<Article>> getAllArticles(@ParameterObject ArticleCriteria criteria){
        List<Article> articles = queryArticleService.findListByCriteria(criteria);
        return ResponseEntity.ok(articles);
    }

    @PutMapping("/admin-articles/{id}/approve")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Void> approve(@PathVariable("id") Long id){
        articleService.approve(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/admin-articles/{id}/reject")
    @PreAuthorize("hasAuthority(\"" + AuthorityConstant.ADMIN + "\")")
    public ResponseEntity<Void> reject(@PathVariable("id") Long id){
        articleService.reject(id);
        return ResponseEntity.noContent().build();
    }
}
