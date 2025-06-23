package com.app.homeworkoutapplication.entity;

import com.app.homeworkoutapplication.entity.enumeration.ArticleStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "article")
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

    @Lob
    @NotNull
    @Column(name = "requirement", nullable = false)
    private String requirement;

    @Column(name = "from_salary")
    private Long fromSalary;

    @Column(name = "to_salary")
    private Long toSalary;

    @NotNull
    @Column(name = "due_date", nullable = false)
    private Instant dueDate;

    @NotNull
    @Size(max = 255)
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate = Instant.now();

    @Column(name = "auto_caculate")
    private Boolean autoCaculate;

    @Column(name = "education_required")
    private String educationRequired;

    @OneToMany(mappedBy = "article")
    private Set<ArticleIndustryEntity> industries = new HashSet<>();

    @OneToMany(mappedBy = "article")
    private Set<ArticleJobLevelEntity> jobLevels = new HashSet<>();

    @OneToMany(mappedBy = "article")
    private Set<ArticleWorkingModelEntity> workingModels = new HashSet<>();

    @OneToMany(mappedBy = "article")
    private Set<ArticleSkillEntity> skills = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleEntity that = (ArticleEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(mainImagePath, that.mainImagePath) && Objects.equals(content, that.content) && Objects.equals(requirement, that.requirement) && Objects.equals(company, that.company) && Objects.equals(fromSalary, that.fromSalary) && Objects.equals(toSalary, that.toSalary) && Objects.equals(dueDate, that.dueDate) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, mainImagePath, content, requirement, company, fromSalary, toSalary, dueDate, status);
    }

    @Override
    public String toString() {
        return "ArticleEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", mainImagePath='" + mainImagePath + '\'' +
                ", content='" + content + '\'' +
                ", requirement='" + requirement + '\'' +
                ", fromSalary=" + fromSalary +
                ", toSalary=" + toSalary +
                ", dueDate=" + dueDate +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
