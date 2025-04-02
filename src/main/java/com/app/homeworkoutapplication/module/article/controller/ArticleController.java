package com.app.homeworkoutapplication.module.article.controller;

import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.service.QueryArticleService;
import com.app.homeworkoutapplication.module.article.service.ArticleService;
import com.app.homeworkoutapplication.module.article.service.criteria.ArticleCriteria;
import com.app.homeworkoutapplication.security.AuthorityConstant;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Authentication")
public class ArticleController {

    private final ArticleService articleService;

    private final QueryArticleService queryArticleService;

    public ArticleController(ArticleService articleService, QueryArticleService queryArticleService) {
        this.articleService = articleService;
        this.queryArticleService = queryArticleService;
    }

    @PostMapping("/articles")
    public ResponseEntity<Article> create(@Valid @RequestBody Article article) throws URISyntaxException {
        Article result = articleService.create(article);
        return ResponseEntity.created(new URI("/api/articles/" + result.getId())).body(result);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable("id") Long id, @Valid @RequestBody Article article){
        if (article.getId() == null) article.setId(id);
        Article res = articleService.update(article);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getArticlePages(@ParameterObject ArticleCriteria criteria, @ParameterObject Pageable pageable) {
        Page<Article> page = queryArticleService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/articles/all")
    public ResponseEntity<List<Article>> getAllArticles(@ParameterObject ArticleCriteria criteria){
        List<Article> articles = queryArticleService.findListByCriteria(criteria);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> getById(@PathVariable("id") Long id){
        Article res = queryArticleService.getById(id);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
