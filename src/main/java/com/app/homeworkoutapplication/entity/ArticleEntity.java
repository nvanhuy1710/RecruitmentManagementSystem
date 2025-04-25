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
import java.util.Objects;

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

    @Lob
    @NotNull
    @Column(name = "requirement", nullable = false)
    private String requirement;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @Size(max = 255)
    @Column(name = "location")
    private String location;

    @Size(max = 500)
    @Column(name = "company")
    private String company;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleEntity that = (ArticleEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(mainImagePath, that.mainImagePath) && Objects.equals(content, that.content) && Objects.equals(requirement, that.requirement) && Objects.equals(address, that.address) && Objects.equals(location, that.location) && Objects.equals(company, that.company) && Objects.equals(fromSalary, that.fromSalary) && Objects.equals(toSalary, that.toSalary) && Objects.equals(dueDate, that.dueDate) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, mainImagePath, content, requirement, address, location, company, fromSalary, toSalary, dueDate, status);
    }
}
