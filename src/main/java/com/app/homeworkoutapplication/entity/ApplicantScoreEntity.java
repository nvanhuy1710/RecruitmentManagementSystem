package com.app.homeworkoutapplication.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

@Data
@Entity
@Table(name = "applicant_score")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplicantScoreEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "overall")
    private Double overall;

    @Column(name = "structure")
    private Double structure;

    @Column(name = "skill")
    private Double skill;

    @Column(name = "experience")
    private Double experience;

    @Column(name = "education")
    private Double education;

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private ApplicantEntity applicant;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicantScoreEntity that = (ApplicantScoreEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(overall, that.overall) && Objects.equals(structure, that.structure) && Objects.equals(skill, that.skill) && Objects.equals(experience, that.experience) && Objects.equals(education, that.education);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, overall, structure, skill, experience, education);
    }

    @Override
    public String toString() {
        return "ApplicantScoreEntity{" +
                "id=" + id +
                ", overall=" + overall +
                ", structure=" + structure +
                ", skill=" + skill +
                ", experience=" + experience +
                ", education=" + education +
                '}';
    }
}
