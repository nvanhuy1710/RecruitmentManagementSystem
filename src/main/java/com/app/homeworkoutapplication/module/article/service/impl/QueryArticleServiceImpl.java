package com.app.homeworkoutapplication.module.article.service.impl;

import com.app.homeworkoutapplication.entity.*;
import com.app.homeworkoutapplication.entity.mapper.*;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.service.QueryArticleService;
import com.app.homeworkoutapplication.module.article.service.criteria.ArticleCriteria;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.module.skill.dto.Skill;
import com.app.homeworkoutapplication.repository.ArticleRepository;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryArticleServiceImpl extends QueryService<ArticleEntity> implements QueryArticleService {

    private final Logger log = LoggerFactory.getLogger(QueryArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    private final SkillMapper skillMapper;

    private final CompanyMapper companyMapper;

    private final ArticleMapper articleMapper;

    private final BlobStorageService blobStorageService;

    private final IndustryMapper industryMapper;

    private final JobLevelMapper jobLevelMapper;

    private final WorkingModelMapper workingModelMapper;

    private final UserMapper userMapper;

    private final CurrentUserUtil currentUserUtil;

    public QueryArticleServiceImpl(ArticleRepository articleRepository, SkillMapper skillMapper, CompanyMapper companyMapper, ArticleMapper articleMapper, BlobStorageService blobStorageService, IndustryMapper industryMapper, JobLevelMapper jobLevelMapper, WorkingModelMapper workingModelMapper, UserMapper userMapper, CurrentUserUtil currentUserUtil) {
        this.articleRepository = articleRepository;
        this.skillMapper = skillMapper;
        this.companyMapper = companyMapper;
        this.articleMapper = articleMapper;
        this.blobStorageService = blobStorageService;
        this.industryMapper = industryMapper;
        this.jobLevelMapper = jobLevelMapper;
        this.workingModelMapper = workingModelMapper;
        this.userMapper = userMapper;
        this.currentUserUtil = currentUserUtil;
    }

    @Override
    public List<Article> findListByCriteria(ArticleCriteria criteria) {
        List<ArticleEntity> entities = articleRepository.findAll(createSpecification(criteria));

        List<Article> articles = entities.stream()
                .map(entity -> {
                    Article article = articleMapper.toDto(entity);
                    fetchData(entity, article);
                    return article;
                })
                .toList();

        if (criteria.getSortByRelated()!= null && criteria.getSortByRelated() && !articles.isEmpty()) {
            List<Long> userSkillIds = currentUserUtil.getCurrentUser().getSkills().stream()
                    .map(Skill::getId)
                    .toList();
            Set<Long> userSkillSet = new HashSet<>(userSkillIds);

            articles = articles.stream()
                    .map(article -> {
                        long matchCount = article.getSkills().stream()
                                .map(Skill::getId)
                                .filter(userSkillSet::contains)
                                .count();
                        return new AbstractMap.SimpleEntry<>(article, matchCount);
                    })
                    .filter(entry -> entry.getValue() > 0)
                    .sorted(Comparator
                            .comparingLong((AbstractMap.SimpleEntry<Article, Long> e) -> e.getValue()).reversed()
                            .thenComparing(e -> e.getKey().getId(), Comparator.reverseOrder()))
                    .map(AbstractMap.SimpleEntry::getKey)
                    .toList();
        }

        return articles;
    }


    @Override
    public Page<Article> findPageByCriteria(ArticleCriteria criteria, Pageable pageable) {
        Page<ArticleEntity> page = articleRepository.findAll(createSpecification(criteria), pageable);

        List<Article> content = page.getContent().stream()
                .map(entity -> {
                    Article article = articleMapper.toDto(entity);
                    fetchData(entity, article);
                    return article;
                })
                .toList();

        if (criteria.getSortByRelated()!= null && criteria.getSortByRelated() && !content.isEmpty()) {
            List<Long> userSkillIds = currentUserUtil.getCurrentUser().getSkills().stream()
                    .map(Skill::getId)
                    .toList();
            Set<Long> userSkillSet = new HashSet<>(userSkillIds);

            content = content.stream()
                    .map(article -> {
                        long matchCount = article.getSkills().stream()
                                .map(Skill::getId)
                                .filter(userSkillSet::contains)
                                .count();
                        return new AbstractMap.SimpleEntry<>(article, matchCount);
                    })
                    .sorted(Comparator
                            .comparingLong((AbstractMap.SimpleEntry<Article, Long> e) -> e.getValue()).reversed()
                            .thenComparing(e -> e.getKey().getId(), Comparator.reverseOrder()))
                    .map(AbstractMap.SimpleEntry::getKey)
                    .toList();
        }

        return new PageImpl<>(content, pageable, page.getTotalElements());
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
        if (criteria.getSalary() != null) {
            specification = specification.and(specFindBySalary(criteria.getSalary()));
        }
        if (criteria.getDueDate() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getDueDate(), ArticleEntity_.dueDate));
        }
        if (criteria.getStatus() != null) {
            specification = specification.and(buildSpecification(criteria.getStatus(), ArticleEntity_.status));
        }
        if (criteria.getCompanyId() != null) {
            specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    articleEntityRoot -> articleEntityRoot.join(ArticleEntity_.company).get(CompanyEntity_.id)));
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
                Join<ArticleIndustryEntity, IndustryEntity> join = root.join(ArticleEntity_.industries, JoinType.LEFT).join(ArticleIndustryEntity_.industry, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(IndustryEntity_.id), idFilter.getEquals());
            };
        }
        if (idFilter.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleIndustryEntity, IndustryEntity> join = root.join(ArticleEntity_.industries, JoinType.LEFT).join(ArticleIndustryEntity_.industry, JoinType.LEFT);
                return join.get(IndustryEntity_.id).in(idFilter.getIn());
            };
        }
        return null;
    }

    private Specification<ArticleEntity> specFindByJobLevelId(LongFilter idFilter) {
        if (idFilter.getEquals() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleJobLevelEntity, JobLevelEntity> join = root.join(ArticleEntity_.jobLevels, JoinType.LEFT).join(ArticleJobLevelEntity_.jobLevel, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(JobLevelEntity_.id), idFilter.getEquals());
            };
        }
        if (idFilter.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleJobLevelEntity, JobLevelEntity> join = root.join(ArticleEntity_.jobLevels, JoinType.LEFT).join(ArticleJobLevelEntity_.jobLevel, JoinType.LEFT);
                return join.get(JobLevelEntity_.id).in(idFilter.getIn());
            };
        }
        return null;
    }

    private Specification<ArticleEntity> specFindByWorkingModelId(LongFilter idFilter) {
        if (idFilter.getEquals() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleWorkingModelEntity, WorkingModelEntity> join = root.join(ArticleEntity_.workingModels, JoinType.LEFT).join(ArticleWorkingModelEntity_.workingModel, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(WorkingModelEntity_.id), idFilter.getEquals());
            };
        }
        if (idFilter.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<ArticleWorkingModelEntity, WorkingModelEntity> join = root.join(ArticleEntity_.workingModels, JoinType.LEFT).join(ArticleWorkingModelEntity_.workingModel, JoinType.LEFT);
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
        if(article.getCompanyId() != null) {
            article.setCompany(companyMapper.toDto(entity.getCompany()));
        }
        if(!Collections.isEmpty(entity.getIndustries())) {
            article.setIndustryIds(entity.getIndustries().stream().map(industry -> industry.getIndustry().getId()).toList());
            article.setIndustries(industryMapper.toDto(entity.getIndustries().stream().map(ArticleIndustryEntity::getIndustry).toList()));
        }
        if(!Collections.isEmpty(entity.getJobLevels())) {
            article.setJobLevelIds(entity.getJobLevels().stream().map(jobLevel -> jobLevel.getJobLevel().getId()).toList());
            article.setJobLevels(jobLevelMapper.toDto(entity.getJobLevels().stream().map(ArticleJobLevelEntity::getJobLevel).toList()));
        }
        if(!Collections.isEmpty(entity.getWorkingModels())) {
            article.setWorkingModelIds(entity.getWorkingModels().stream().map(workingModel -> workingModel.getWorkingModel().getId()).toList());
            article.setWorkingModels(workingModelMapper.toDto(entity.getWorkingModels().stream().map(ArticleWorkingModelEntity::getWorkingModel).toList()));
        }
        if(!Collections.isEmpty(entity.getSkills())) {
            article.setSkillIds(entity.getSkills().stream().map(workingModel -> workingModel.getSkill().getId()).toList());
            article.setSkills(skillMapper.toDto(entity.getSkills().stream().map(ArticleSkillEntity::getSkill).toList()));
        }
        if(article.getUser() != null) {
            article.setUser(userMapper.toDto(entity.getUser()));
        }
    }
}

