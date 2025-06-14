package com.app.homeworkoutapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "article_industry")
public class ArticleIndustryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private IndustryEntity industry;
}
