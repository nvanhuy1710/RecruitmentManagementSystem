package com.app.homeworkoutapplication.module.article.service.impl;

import com.app.homeworkoutapplication.entity.*;
import com.app.homeworkoutapplication.entity.mapper.*;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.service.QueryArticleService;
import com.app.homeworkoutapplication.module.article.service.criteria.ArticleCriteria;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.repository.ArticleRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryArticleServiceImpl extends QueryService<ArticleEntity> implements QueryArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;

    private final BlobStorageService blobStorageService;

    private final IndustryMapper industryMapper;

    private final JobLevelMapper jobLevelMapper;

    private final WorkingModelMapper workingModelMapper;

    private final UserMapper userMapper;

    public QueryArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper, BlobStorageService blobStorageService, IndustryMapper industryMapper, JobLevelMapper jobLevelMapper, WorkingModelMapper workingModelMapper, UserMapper userMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.blobStorageService = blobStorageService;
        this.industryMapper = industryMapper;
        this.jobLevelMapper = jobLevelMapper;
        this.workingModelMapper = workingModelMapper;
        this.userMapper = userMapper;
    }

    public List<Article> findListByCriteria(ArticleCriteria criteria) {
        return articleRepository.findAll(createSpecification(criteria)).stream().map(
            articleEntity -> {
                Article article = articleMapper.toDto(articleEntity);
                fetchData(articleEntity, article);
                return article;
            }
        ).toList();
    }

    public Page<Article> findPageByCriteria(ArticleCriteria criteria, Pageable pageable) {
        Page<ArticleEntity> page = articleRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
            page.getContent().stream().map(
                articleEntity -> {
                    Article article = articleMapper.toDto(articleEntity);
                    fetchData(articleEntity, article);
                    return article;
                }).toList(),
            pageable,
            page.getTotalElements()
        );
    }

    public Article getById(Long id) {
        Optional<ArticleEntity> articleEntity = articleRepository.findById(id);
        if (articleEntity.isEmpty()) {
            throw new NotFoundException("Not found article by id " + id);
        }
        Article article = articleMapper.toDto(articleEntity.get());
        fetchData(articleEntity.get(), article);
        return article;
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
            specification = specification.and(specFindBySalary(criteria.getSalary()));
        }
        if (criteria.getDueDate() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getDueDate(), ArticleEntity_.dueDate));
        }
        if (criteria.getStatus() != null) {
            specification = specification.and(buildSpecification(criteria.getStatus(), ArticleEntity_.status));
        }
        if (criteria.getIndustryId() != null) {
            specification = specification.and(specFindByIndustryId(criteria.getIndustryId()));
        }
        if (criteria.getJobLevelId() != null) {
            specification = specification.and(specFindByJobLevelId(criteria.getJobLevelId()));
        }
        if (criteria.getWorkingModelId() != null) {
            specification = specification.and(specFindByWorkingModelId(criteria.getWorkingModelId()));
        }
        if (criteria.getUserId() != null) {
            specification = specification.and(specFindByUserId(criteria.getUserId()));
        }

        return specification;
    }

    private Specification<ArticleEntity> specFindByUserId(LongFilter userId) {
        if (userId.getEquals() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleEntity, UserEntity> join = root.join(ArticleEntity_.user, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(UserEntity_.id), userId.getEquals());
            };
        }
        if (userId.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleEntity, UserEntity> join = root.join(ArticleEntity_.user, JoinType.LEFT);
                return join.get(UserEntity_.id).in(userId.getIn());
            };
        }
        return null;
    }

    private Specification<ArticleEntity> specFindByIndustryId(LongFilter idFilter) {
        if (idFilter.getEquals() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleEntity, IndustryEntity> join = root.join(ArticleEntity_.industry, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(IndustryEntity_.id), idFilter.getEquals());
            };
        }
        if (idFilter.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleEntity, IndustryEntity> join = root.join(ArticleEntity_.industry, JoinType.LEFT);
                return join.get(IndustryEntity_.id).in(idFilter.getIn());
            };
        }
        return null;
    }

    private Specification<ArticleEntity> specFindByJobLevelId(LongFilter idFilter) {
        if (idFilter.getEquals() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleEntity, JobLevelEntity> join = root.join(ArticleEntity_.jobLevel, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(JobLevelEntity_.id), idFilter.getEquals());
            };
        }
        if (idFilter.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleEntity, JobLevelEntity> join = root.join(ArticleEntity_.jobLevel, JoinType.LEFT);
                return join.get(JobLevelEntity_.id).in(idFilter.getIn());
            };
        }
        return null;
    }

    private Specification<ArticleEntity> specFindByWorkingModelId(LongFilter idFilter) {
        if (idFilter.getEquals() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleEntity, WorkingModelEntity> join = root.join(ArticleEntity_.workingModel, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(WorkingModelEntity_.id), idFilter.getEquals());
            };
        }
        if (idFilter.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleEntity, WorkingModelEntity> join = root.join(ArticleEntity_.workingModel, JoinType.LEFT);
                return join.get(WorkingModelEntity_.id).in(idFilter.getIn());
            };
        }
        return null;
    }


    private Specification<ArticleEntity> specFindBySalary(LongFilter salary) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (salary.getGreaterThanOrEqual() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get(ArticleEntity_.fromSalary),
                        salary.getGreaterThanOrEqual()));
            }

            if (salary.getLessThanOrEqual() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get(ArticleEntity_.toSalary),
                        salary.getLessThanOrEqual()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void fetchData(ArticleEntity entity, Article article) {
        if(article.getMainImagePath() != null) {
            article.setMainImageUrl(blobStorageService.getUrl(article.getMainImagePath()));
        }
        if(article.getIndustry() != null) {
            article.setIndustry(industryMapper.toDto(entity.getIndustry()));
        }
        if(article.getJobLevel() != null) {
            article.setJobLevel(jobLevelMapper.toDto(entity.getJobLevel()));
        }
        if(article.getWorkingModel() != null) {
            article.setWorkingModel(workingModelMapper.toDto(entity.getWorkingModel()));
        }
        if(article.getUser() != null) {
            article.setUser(userMapper.toDto(entity.getUser()));
        }
    }
}

