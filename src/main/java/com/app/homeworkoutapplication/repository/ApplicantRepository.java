package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.ApplicantEntity;
import com.app.homeworkoutapplication.module.dashboard.dto.ApplicantStatistic;
import com.app.homeworkoutapplication.module.dashboard.dto.ArticleCompanyStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Long>, JpaSpecificationExecutor<ApplicantEntity> {

    @Query(value = """
        SELECT COUNT(*) AS count, DATE_FORMAT(applicant.created_date, '%m') AS month
        FROM applicant
        JOIN article ON applicant.article_id = article.id
        WHERE YEAR(applicant.created_date) = :year
          AND article.user_id = :userId
        GROUP BY DATE_FORMAT(applicant.created_date, '%m')
        ORDER BY month
        """, nativeQuery = true)
    List<ApplicantStatistic> countByMonthInYear(@Param("year") int year, @Param("userId") int userId);
}
