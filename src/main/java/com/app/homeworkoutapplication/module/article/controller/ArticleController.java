package com.app.homeworkoutapplication.module.article.controller;

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
public class ArticleController {

    private final ArticleService articleService;

    private final QueryArticleService queryArticleService;

    private final CurrentUserUtil currentUserUtil;

    public ArticleController(ArticleService articleService, QueryArticleService queryArticleService, CurrentUserUtil currentUserUtil) {
        this.articleService = articleService;
        this.queryArticleService = queryArticleService;
        this.currentUserUtil = currentUserUtil;
    }

    @PostMapping("/articles")
    public ResponseEntity<Article> create(@RequestPart("article") Article article, @RequestPart("image")MultipartFile file) throws URISyntaxException {
        Article result = articleService.create(article, file);
        return ResponseEntity.created(new URI("/api/articles/" + result.getId())).body(result);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable("id") Long id, @Valid @RequestBody Article article){
        if (article.getId() == null) article.setId(id);
        Article res = articleService.update(article);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/articles/{id}/close")
    public ResponseEntity<Article> close(@PathVariable("id") Long id){
        Article res = articleService.close(id);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/articles/{id}/update-image")
    public ResponseEntity<Article> updateImg(@PathVariable("id") Long id,@RequestParam("file") MultipartFile file){
        articleService.updateImage(id, file);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getArticlePages(@ParameterObject ArticleCriteria criteria, @ParameterObject Pageable pageable) {
        criteria.setUserId(setCurrentUser());
        Page<Article> page = queryArticleService.findPageByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/articles/all")
    public ResponseEntity<List<Article>> getAllArticles(@ParameterObject ArticleCriteria criteria){
        criteria.setUserId(setCurrentUser());
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

    private LongFilter setCurrentUser() {
        LongFilter id = new LongFilter();
        id.setEquals(currentUserUtil.getCurrentUser().getId());
        return id;
    }
}
