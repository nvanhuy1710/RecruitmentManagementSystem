package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.ApplicantEntity;
import com.app.homeworkoutapplication.entity.ArticleIndustryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleIndustryRepository extends JpaRepository<ArticleIndustryEntity, Long>, JpaSpecificationExecutor<ArticleIndustryEntity> {
    void deleteByArticleIdAndIndustryId(Long articleId, Long industryId);
    List<ArticleIndustryEntity> findByArticleId(Long articleId);
}