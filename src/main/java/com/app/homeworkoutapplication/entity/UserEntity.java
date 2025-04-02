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

    @Email
    @Size(min = 60, max = 60)
    @Column(name = "email", length = 60, nullable = false)
    private String email;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "username", length = 60, nullable = false)
    private String username;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Column(name = "birthday")
    private Instant birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Column(name = "is_activated")
    private Boolean isActivated;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @ManyToOne
    private RoleEntity role;
}
