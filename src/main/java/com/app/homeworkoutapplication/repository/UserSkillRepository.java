package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.UserSkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkillEntity, Long>, JpaSpecificationExecutor<UserSkillEntity> {
}
