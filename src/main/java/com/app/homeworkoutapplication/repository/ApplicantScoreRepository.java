package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.ApplicantScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantScoreRepository extends JpaRepository<ApplicantScoreEntity, Long>, JpaSpecificationExecutor<ApplicantScoreEntity> {

}
