package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long>, JpaSpecificationExecutor<DocumentEntity> {

    Optional<DocumentEntity> findByName(String name);
}
