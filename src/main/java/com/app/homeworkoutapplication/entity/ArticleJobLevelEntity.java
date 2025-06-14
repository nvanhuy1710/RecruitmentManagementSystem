package com.app.homeworkoutapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "article_job_level")
public class ArticleJobLevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "job_level_id")
    private JobLevelEntity jobLevel;
}
