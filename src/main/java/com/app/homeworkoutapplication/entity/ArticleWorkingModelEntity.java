package com.app.homeworkoutapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "article_working_model")
public class ArticleWorkingModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "working_model_id")
    private WorkingModelEntity workingModel;
}
