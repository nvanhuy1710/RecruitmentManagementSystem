package com.app.homeworkoutapplication.module.usernotification.service;

import com.app.homeworkoutapplication.module.usernotification.dto.UserNotification;

public interface UserNotificationService {

    UserNotification create(UserNotification notification);

    void updateViewed(Long id);
}
