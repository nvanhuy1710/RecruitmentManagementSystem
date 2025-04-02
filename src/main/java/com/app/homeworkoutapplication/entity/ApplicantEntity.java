package com.app.homeworkoutapplication.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "applicant")
@ToString
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplicantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull
    @Size(max = 255)
    @Column(name = "phone", nullable = false)
    private String phone;

    @Size(max = 255)
    @Column(name = "cover_letter")
    private String coverLetter;

    @NotNull
    @Size(max = 255)
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Size(max = 255)
    @Column(name = "resume_path", nullable = false)
    private String resumePath;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
