package com.app.homeworkoutapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "article_skill")
public class ArticleSkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private SkillEntity skill;
}