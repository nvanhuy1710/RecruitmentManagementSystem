package com.app.homeworkoutapplication.module.article.service.impl;

import com.app.homeworkoutapplication.entity.ArticleEntity;
import com.app.homeworkoutapplication.entity.ArticleEntity_;
import com.app.homeworkoutapplication.entity.mapper.ArticleMapper;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.service.QueryArticleService;
import com.app.homeworkoutapplication.module.article.service.criteria.ArticleCriteria;
import com.app.homeworkoutapplication.repository.ArticleRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryArticleServiceImpl extends QueryService<ArticleEntity> implements QueryArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;


    public QueryArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    public List<Article> findListByCriteria(ArticleCriteria criteria) {
        return articleRepository.findAll(createSpecification(criteria)).stream().map(articleMapper::toDto).toList();
    }

    public Page<Article> findPageByCriteria(ArticleCriteria criteria, Pageable pageable) {
        Page<ArticleEntity> page =  articleRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(articleMapper::toDto).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    public Article getById(Long id) {
        Optional<ArticleEntity> articleEntity = articleRepository.findById(id);
        if (articleEntity.isEmpty()) {
            throw new NotFoundException("Not found article by id " + id);
        }
        return articleMapper.toDto(articleEntity.get());
    }

    private Specification<ArticleEntity> createSpecification(ArticleCriteria criteria) {
        Specification<ArticleEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), ArticleEntity_.id));
        }
        if (criteria.getTitle() != null) {
            specification = specification.and(buildStringSpecification(criteria.getTitle(), ArticleEntity_.title));
        }
        if (criteria.getContent() != null) {
            specification = specification.and(buildStringSpecification(criteria.getContent(), ArticleEntity_.content));
        }
        if (criteria.getAddress() != null) {
            specification = specification.and(buildStringSpecification(criteria.getAddress(), ArticleEntity_.address));
        }
        if (criteria.getLocation() != null) {
            specification = specification.and(buildStringSpecification(criteria.getLocation(), ArticleEntity_.location));
        }
        if (criteria.getSalary() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getSalary(), ArticleEntity_.salary));
        }
        if (criteria.getDueDate() != null) {
            specification = specification.and(buildSpecification(criteria.getDueDate(), ArticleEntity_.dueDate));
        }
        if (criteria.getStatus() != null) {
            specification = specification.and(buildSpecification(criteria.getStatus(), ArticleEntity_.status));
        }

        return specification;
    }
}
