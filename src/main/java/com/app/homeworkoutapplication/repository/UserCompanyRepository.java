package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.UserCompanyEntity;
import com.app.homeworkoutapplication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCompanyRepository extends JpaRepository<UserCompanyEntity, Long>, JpaSpecificationExecutor<UserCompanyEntity> {

    Optional<UserCompanyEntity> findByUserIdAndCompanyId(@Param("userId") Long userId, @Param("companyId") Long companyId);

    List<UserCompanyEntity> findByCompanyId(@Param("companyId") Long companyId);

    List<UserCompanyEntity> findByUserId(@Param("userId") Long userId);
}
