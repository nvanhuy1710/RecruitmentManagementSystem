package com.app.homeworkoutapplication.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "user_notification")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserNotificationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "data")
    private String data;

    @Column(name = "viewed")
    private Boolean viewed;

    @ManyToOne
//    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserNotificationEntity entity = (UserNotificationEntity) o;
        return Objects.equals(id, entity.id) && Objects.equals(articleId, entity.articleId) && Objects.equals(viewed, entity.viewed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articleId, viewed);
    }

    @Override
    public String toString() {
        return "UserNotificationEntity{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", companyName='" + companyName + '\'' +
                ", data='" + data + '\'' +
                ", viewed=" + viewed +
                '}';
    }
}
