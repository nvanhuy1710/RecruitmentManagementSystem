package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long>, JpaSpecificationExecutor<ArticleEntity> {
}
