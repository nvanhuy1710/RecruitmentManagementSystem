package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.UserCompanyEntity;
import com.app.homeworkoutapplication.entity.UserEntity;
import com.app.homeworkoutapplication.module.user.dto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    @Query("select user from UserEntity user " +
            "join user.userCompanies uc " +
            "where  uc.company.id = :companyId")
    List<UserEntity> findListFollowByCompanyId(@Param("companyId") Long companyId);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);
}
