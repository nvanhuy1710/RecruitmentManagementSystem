package com.app.homeworkoutapplication.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "industry")
@ToString
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IndustryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "industry")
    private Set<ArticleEntity> articles = new HashSet<>();
}

