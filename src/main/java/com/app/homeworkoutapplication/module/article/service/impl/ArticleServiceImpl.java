package com.app.homeworkoutapplication.module.article.service.impl;

import com.app.homeworkoutapplication.entity.*;
import com.app.homeworkoutapplication.entity.enumeration.ArticleStatus;
import com.app.homeworkoutapplication.entity.mapper.ArticleMapper;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.dto.event.ApproveArticleEvent;
import com.app.homeworkoutapplication.module.article.service.ArticleService;
import com.app.homeworkoutapplication.module.article.service.QueryArticleService;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.repository.*;
import com.app.homeworkoutapplication.util.BlobStoragePathUtil;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ArticleIndustryRepository articleIndustryRepository;

    private final ArticleJobLevelRepository articleJobLevelRepository;

    private final ArticleWorkingModelRepository articleWorkingModelRepository;

    private final ArticleSkillRepository articleSkillRepository;

    private final QueryArticleService queryArticleService;

    private final ArticleMapper articleMapper;

    private final BlobStorageService blobStorageService;

    private final BlobStoragePathUtil blobStoragePathUtil;

    private final CurrentUserUtil currentUserUtil;

    private final ApplicationEventPublisher applicationEventPublisher;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleIndustryRepository articleIndustryRepository, ArticleJobLevelRepository articleJobLevelRepository, ArticleWorkingModelRepository articleWorkingModelRepository, ArticleSkillRepository articleSkillRepository, QueryArticleService queryArticleService, ArticleMapper articleMapper, BlobStorageService blobStorageService, BlobStoragePathUtil blobStoragePathUtil, CurrentUserUtil currentUserUtil, ApplicationEventPublisher applicationEventPublisher) {
        this.articleRepository = articleRepository;
        this.articleIndustryRepository = articleIndustryRepository;
        this.articleJobLevelRepository = articleJobLevelRepository;
        this.articleWorkingModelRepository = articleWorkingModelRepository;
        this.articleSkillRepository = articleSkillRepository;
        this.queryArticleService = queryArticleService;
        this.articleMapper = articleMapper;
        this.blobStorageService = blobStorageService;
        this.blobStoragePathUtil = blobStoragePathUtil;
        this.currentUserUtil = currentUserUtil;
        this.applicationEventPublisher = applicationEventPublisher;
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

        List<Long> industryIds = article.getIndustryIds();
        List<Long> jobLevelIds = article.getJobLevelIds();
        List<Long> workingModelIds = article.getWorkingModelIds();
        List<Long> skillIds = article.getSkillIds();

        article = articleMapper.toDto(articleRepository.save(articleMapper.toEntity(article)));

        saveIndustry(article.getId(), industryIds);
        saveJobLevel(article.getId(), jobLevelIds);
        saveWorkingModel(article.getId(), workingModelIds);
        saveSkill(article.getId(), skillIds);

        return article;
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

        List<Long> industryIds = article.getIndustryIds();
        List<Long> jobLevelIds = article.getJobLevelIds();
        List<Long> workingModelIds = article.getWorkingModelIds();
        List<Long> skillIds = article.getSkillIds();

        article = articleMapper.toDto(articleRepository.save(articleMapper.toEntity(article)));

        saveIndustry(article.getId(), industryIds);
        saveJobLevel(article.getId(), jobLevelIds);
        saveWorkingModel(article.getId(), workingModelIds);
        saveSkill(article.getId(), skillIds);

        return article;
    }

    @Override
    public Article close(Long id) {
        articleRepository.updateStatusById(id, ArticleStatus.CLOSED);
        return queryArticleService.getById(id);
    }

    public Article approve(Long id) {
        articleRepository.updateStatusById(id, ArticleStatus.APPROVED);
        Article article = queryArticleService.getById(id);
        applicationEventPublisher.publishEvent(new ApproveArticleEvent(this, article));
        return article;
    }

    public Article reject(Long id) {
        articleRepository.updateStatusById(id, ArticleStatus.REJECTED);
        return queryArticleService.getById(id);
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    private void saveIndustry(Long articleId, List<Long> industryIds) {
        Article article = queryArticleService.getById(articleId);

        Set<Long> currentIds = article.getIndustryIds() != null ? new HashSet<>(article.getIndustryIds()) : Collections.emptySet();

        Set<Long> newIds = new HashSet<>(industryIds);

        Set<Long> toAdds = new HashSet<>(newIds);
        toAdds.removeAll(currentIds);

        Set<Long> toRemoves = new HashSet<>(currentIds);
        toRemoves.removeAll(newIds);

        for(Long toAdd : toAdds) {
            ArticleIndustryEntity entity = new ArticleIndustryEntity();

            IndustryEntity industry = new IndustryEntity();
            industry.setId(toAdd);

            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setId(articleId);

            entity.setIndustry(industry);
            entity.setArticle(articleEntity);

            articleIndustryRepository.save(entity);
        }

        for(Long toRemove : toRemoves) {
            articleIndustryRepository.deleteByArticleIdAndIndustryId(articleId, toRemove);
        }
    }

    private void saveJobLevel(Long articleId, List<Long> industryIds) {
        Article article = queryArticleService.getById(articleId);

        Set<Long> currentIds = article.getJobLevelIds() != null ? new HashSet<>(article.getJobLevelIds()) : Collections.emptySet();

        Set<Long> newIds = new HashSet<>(industryIds);

        Set<Long> toAdds = new HashSet<>(newIds);
        toAdds.removeAll(currentIds);

        Set<Long> toRemoves = new HashSet<>(currentIds);
        toRemoves.removeAll(newIds);

        for(Long toAdd : toAdds) {
            ArticleJobLevelEntity entity = new ArticleJobLevelEntity();

            JobLevelEntity jobLevel = new JobLevelEntity();
            jobLevel.setId(toAdd);

            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setId(articleId);

            entity.setJobLevel(jobLevel);
            entity.setArticle(articleEntity);

            articleJobLevelRepository.save(entity);
        }

        for(Long toRemove : toRemoves) {
            articleJobLevelRepository.deleteByArticleIdAndJobLevelId(articleId, toRemove);
        }
    }

    private void saveWorkingModel(Long articleId, List<Long> industryIds) {
        Article article = queryArticleService.getById(articleId);

        Set<Long> currentIds = article.getWorkingModelIds() != null ? new HashSet<>(article.getWorkingModelIds()) : Collections.emptySet();

        Set<Long> newIds = new HashSet<>(industryIds);

        Set<Long> toAdds = new HashSet<>(newIds);
        toAdds.removeAll(currentIds);

        Set<Long> toRemoves = new HashSet<>(currentIds);
        toRemoves.removeAll(newIds);

        for(Long toAdd : toAdds) {
            ArticleWorkingModelEntity entity = new ArticleWorkingModelEntity();

            WorkingModelEntity industry = new WorkingModelEntity();
            industry.setId(toAdd);

            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setId(articleId);

            entity.setWorkingModel(industry);
            entity.setArticle(articleEntity);

            articleWorkingModelRepository.save(entity);
        }

        for(Long toRemove : toRemoves) {
            articleWorkingModelRepository.deleteByArticleIdAndWorkingModelId(articleId, toRemove);
        }
    }

    private void saveSkill(Long articleId, List<Long> skillIds) {
        Article article = queryArticleService.getById(articleId);

        Set<Long> currentIds = article.getSkillIds() != null ? new HashSet<>(article.getSkillIds()) : Collections.emptySet();

        Set<Long> newIds = new HashSet<>(skillIds);

        Set<Long> toAdds = new HashSet<>(newIds);
        toAdds.removeAll(currentIds);

        Set<Long> toRemoves = new HashSet<>(currentIds);
        toRemoves.removeAll(newIds);

        for(Long toAdd : toAdds) {
            ArticleSkillEntity entity = new ArticleSkillEntity();

            SkillEntity skill = new SkillEntity();
            skill.setId(toAdd);

            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setId(articleId);

            entity.setSkill(skill);
            entity.setArticle(articleEntity);

            articleSkillRepository.save(entity);
        }

        for(Long toRemove : toRemoves) {
            articleSkillRepository.deleteByArticleIdAndSkillId(articleId, toRemove);
        }
    }
}
