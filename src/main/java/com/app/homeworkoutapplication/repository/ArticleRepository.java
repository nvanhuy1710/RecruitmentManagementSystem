package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.ArticleEntity;
import com.app.homeworkoutapplication.entity.enumeration.ArticleStatus;
import com.app.homeworkoutapplication.module.dashboard.dto.ArticleCompanyStatistic;
import com.app.homeworkoutapplication.module.dashboard.dto.ArticleStatistic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long>, JpaSpecificationExecutor<ArticleEntity> {

    @Query("""
        SELECT a FROM ArticleEntity a
        JOIN a.skills us
        JOIN us.skill s
        WHERE s.id IN :userSkillIds
        GROUP BY a
        HAVING COUNT(s.id) > 0
        ORDER BY COUNT(s.id) DESC, a.id DESC
    """)
    Page<ArticleEntity> findPageFeaturedByUserSkills(@Param("userSkillIds") List<Long> userSkillIds, Pageable pageable);

    @Query("""
        SELECT a FROM ArticleEntity a
        JOIN a.skills us
        JOIN us.skill s
        WHERE s.id IN :userSkillIds
        GROUP BY a
        HAVING COUNT(s.id) > 0
        ORDER BY COUNT(s.id) DESC, a.id DESC
    """)
    List<ArticleEntity> findListFeaturedByUserSkills(@Param("userSkillIds") List<Long> userSkillIds);

    @Modifying
    @Query("UPDATE ArticleEntity a SET a.status = :status WHERE a.id = :id")
    int updateStatusById(@Param("id") Long id, @Param("status") ArticleStatus status);

    @Query(value = """
        SELECT COUNT(*) AS count, DATE_FORMAT(created_date, '%m') AS month
        FROM article
        WHERE YEAR(created_date) = :year
          AND article.user_id = :userId
        GROUP BY DATE_FORMAT(created_date, '%m')
        ORDER BY month
        """, nativeQuery = true)
    List<ArticleStatistic> countByMonthInYear(@Param("year") int year, @Param("userId") int userId);

    @Query(value = """
        SELECT COUNT(*) AS count, c.name AS companyName
        FROM article a
        JOIN company c ON a.company_id = c.id
        WHERE YEAR(a.created_date) = :year
          AND a.user_id = :userId
        GROUP BY c.name
        ORDER BY count DESC
        """, nativeQuery = true)
    List<ArticleCompanyStatistic> countByCompanyInYear(@Param("year") int year, @Param("userId") int userId);
}
