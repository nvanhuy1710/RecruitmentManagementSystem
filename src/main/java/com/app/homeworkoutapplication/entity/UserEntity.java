package com.app.homeworkoutapplication.entity;

import com.app.homeworkoutapplication.entity.enumeration.Level;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
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

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @OneToMany(mappedBy = "user")
    private Set<ArticleEntity> articles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ApplicantEntity> applicants = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserSkillEntity> userSkills = new HashSet<>();
}
