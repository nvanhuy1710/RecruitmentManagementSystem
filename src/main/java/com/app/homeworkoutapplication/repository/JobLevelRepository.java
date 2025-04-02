package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.JobLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobLevelRepository extends JpaRepository<JobLevelEntity, Long>, JpaSpecificationExecutor<JobLevelEntity> {

    Optional<JobLevelEntity> findByName(String name);
}
