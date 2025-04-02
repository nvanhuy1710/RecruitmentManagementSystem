package com.app.homeworkoutapplication.entity;

import com.app.homeworkoutapplication.entity.enumeration.ArticleStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Table(name = "article")
@ToString
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ArticleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Size(max = 255)
    @Column(name = "main_image_path")
    private String mainImagePath;

    @Lob
    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @Size(max = 255)
    @Column(name = "location")
    private String location;

    @Size(max = 255)
    @Column(name = "company_website_url")
    private String companyWebsiteUrl;

    @Column(name = "salary")
    private Integer salary;

    @NotNull
    @Column(name = "due_date", nullable = false)
    private Instant dueDate;

    @NotNull
    @Size(max = 255)
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @ManyToOne
    @JoinColumn(name = "industry_id")
    private IndustryEntity industry;

    @ManyToOne
    @JoinColumn(name = "job_level_id")
    private JobLevelEntity jobLevel;

    @ManyToOne
    @JoinColumn(name = "working_model_id")
    private WorkingModelEntity workingModel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
