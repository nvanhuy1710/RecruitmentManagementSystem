package com.app.homeworkoutapplication.repository;

import com.app.homeworkoutapplication.entity.UserNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotificationEntity, Long>, JpaSpecificationExecutor<UserNotificationEntity> {
}
