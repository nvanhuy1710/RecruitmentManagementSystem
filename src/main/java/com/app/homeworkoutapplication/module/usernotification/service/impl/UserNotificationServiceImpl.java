package com.app.homeworkoutapplication.module.usernotification.service.impl;

import com.app.homeworkoutapplication.entity.UserNotificationEntity;
import com.app.homeworkoutapplication.entity.mapper.UserNotificationMapper;
import com.app.homeworkoutapplication.module.usernotification.dto.UserNotification;
import com.app.homeworkoutapplication.module.usernotification.dto.event.UserNotificationCreatedEvent;
import com.app.homeworkoutapplication.module.usernotification.service.QueryUserNotificationService;
import com.app.homeworkoutapplication.module.usernotification.service.UserNotificationService;
import com.app.homeworkoutapplication.repository.UserNotificationRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserNotificationServiceImpl implements UserNotificationService {

    private final UserNotificationRepository userNotificationRepository;

    private final UserNotificationMapper userNotificationMapper;

    private final QueryUserNotificationService queryUserNotificationService;

    private final ApplicationEventPublisher applicationEventPublisher;

    public UserNotificationServiceImpl(UserNotificationRepository userNotificationRepository, UserNotificationMapper userNotificationMapper, QueryUserNotificationService queryUserNotificationService, ApplicationEventPublisher applicationEventPublisher) {
        this.userNotificationRepository = userNotificationRepository;
        this.userNotificationMapper = userNotificationMapper;
        this.queryUserNotificationService = queryUserNotificationService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public UserNotification create(UserNotification notification) {
        notification.setViewed(false);
        UserNotification res = userNotificationMapper.toDto(userNotificationRepository.save(userNotificationMapper.toEntity(notification)));
        applicationEventPublisher.publishEvent(new UserNotificationCreatedEvent(this, res));
        return res;
    }

    @Override
    public void updateViewed(Long id) {
        UserNotification notification = queryUserNotificationService.getById(id);
        notification.setViewed(true);
        userNotificationRepository.save(userNotificationMapper.toEntity(notification));
    }
}
