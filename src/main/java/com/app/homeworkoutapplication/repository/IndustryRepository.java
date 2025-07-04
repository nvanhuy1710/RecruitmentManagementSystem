package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.IndustryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndustryRepository extends JpaRepository<IndustryEntity, Long>, JpaSpecificationExecutor<IndustryEntity> {
    Optional<IndustryEntity> findByName(String name);
}
