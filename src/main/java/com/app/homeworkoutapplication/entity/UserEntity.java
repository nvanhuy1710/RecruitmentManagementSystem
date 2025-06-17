package com.app.homeworkoutapplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
@ToString
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @Column(name = "full_name")
    private String fullName;

    @Size(max = 255)
    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Size(max = 500)
    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "birth")
    private Instant birth;

    @Size(max = 256)
    @Column(name = "avatar_path", length = 256)
    private String avatarPath;

    @NotNull
    @Column(name = "is_activated", nullable = false)
    private Boolean isActivated;

    @Column(name = "locked")
    private Boolean locked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<ArticleEntity> articles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ApplicantEntity> applicants = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserSkillEntity> userSkills = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserCompanyEntity> userCompanies = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserNotificationEntity> notifications = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(fullName, that.fullName) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(gender, that.gender) && Objects.equals(birth, that.birth) && Objects.equals(avatarPath, that.avatarPath) && Objects.equals(isActivated, that.isActivated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, fullName, username, password, gender, birth, avatarPath, isActivated);
    }
}
