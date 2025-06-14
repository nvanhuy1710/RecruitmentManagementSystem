package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.ArticleIndustryEntity;
import com.app.homeworkoutapplication.entity.ArticleJobLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleJobLevelRepository extends JpaRepository<ArticleJobLevelEntity, Long>, JpaSpecificationExecutor<ArticleJobLevelEntity> {
    void deleteByArticleIdAndJobLevelId(Long articleId, Long jobLevelId);

} 