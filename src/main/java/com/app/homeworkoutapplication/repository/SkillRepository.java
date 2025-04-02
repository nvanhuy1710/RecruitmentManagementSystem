package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<SkillEntity, Long>, JpaSpecificationExecutor<SkillEntity> {
    Optional<SkillEntity> findByName(String name);
}
