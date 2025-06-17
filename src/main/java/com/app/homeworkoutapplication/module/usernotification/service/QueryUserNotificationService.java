package com.app.homeworkoutapplication.module.usernotification.service;

import com.app.homeworkoutapplication.module.usernotification.dto.UserNotification;
import com.app.homeworkoutapplication.module.usernotification.service.criteria.UserNotificationCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryUserNotificationService {

    List<UserNotification> findListByCriteria(UserNotificationCriteria criteria);

    Page<UserNotification> findByUserId(Long userId, Pageable pageable);

    long countByUserId(Long userId);

    Page<UserNotification> findPageByCriteria(UserNotificationCriteria criteria, Pageable pageable);

    UserNotification getById(Long id);
}
