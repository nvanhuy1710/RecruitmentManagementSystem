package com.app.homeworkoutapplication.entity;

import com.app.homeworkoutapplication.entity.enumeration.ApplicantStatus;
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
@Table(name = "applicant")
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

    @Column(name = "match_score")
    private Double matchScore;

    @NotNull
    @Size(max = 255)
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicantStatus status;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Instant createdDate = Instant.now();

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate = Instant.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "applicant")
    private Set<DocumentEntity> documents = new HashSet<>();

    @OneToMany(mappedBy = "applicant")
    private Set<ApplicantScoreEntity> applicantScores = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicantEntity that = (ApplicantEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(fullName, that.fullName) && Objects.equals(phone, that.phone) && Objects.equals(coverLetter, that.coverLetter) && status == that.status && Objects.equals(createdDate, that.createdDate) && Objects.equals(lastModifiedDate, that.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, phone, coverLetter, status, createdDate, lastModifiedDate);
    }

    @Override
    public String toString() {
        return "ApplicantEntity{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", coverLetter='" + coverLetter + '\'' +
                ", matchScore=" + matchScore +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
