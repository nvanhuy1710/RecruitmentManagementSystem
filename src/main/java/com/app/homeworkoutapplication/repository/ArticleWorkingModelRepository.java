package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.ArticleSkillEntity;
import com.app.homeworkoutapplication.entity.ArticleWorkingModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleWorkingModelRepository extends JpaRepository<ArticleWorkingModelEntity, Long>, JpaSpecificationExecutor<ArticleWorkingModelEntity> {
    void deleteByArticleIdAndWorkingModelId(Long articleId, Long workingModelId);

    List<ArticleWorkingModelEntity> findByArticleId(Long articleId);
}
