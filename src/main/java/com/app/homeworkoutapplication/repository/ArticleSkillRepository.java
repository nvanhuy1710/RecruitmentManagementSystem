package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.ArticleJobLevelEntity;
import com.app.homeworkoutapplication.entity.ArticleSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleSkillRepository extends JpaRepository<ArticleSkillEntity, Long>, JpaSpecificationExecutor<ArticleSkillEntity> {
    void deleteByArticleIdAndSkillId(Long articleId, Long skillId);

    List<ArticleSkillEntity> findByArticleId(Long articleId);
}
