package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.ApplicantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Long>, JpaSpecificationExecutor<ApplicantEntity> {
    Optional<ApplicantEntity> findByEmail(String email);
}
