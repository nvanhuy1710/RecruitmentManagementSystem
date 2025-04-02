package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.WorkingModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkingModelRepository extends JpaRepository<WorkingModelEntity, Long>, JpaSpecificationExecutor<WorkingModelEntity> {
    Optional<WorkingModelEntity> findByName(String name);
}
