package com.app.homeworkoutapplication.module.usernotification.dto.event;

import com.app.homeworkoutapplication.common.Event;
import com.app.homeworkoutapplication.module.usernotification.dto.UserNotification;

public class UserNotificationCreatedEvent extends Event<UserNotification> {

    public UserNotificationCreatedEvent(Object source, UserNotification userNotification) {
        super(source, userNotification);
    }

    @Override
    public String getName() {
        return "usernotification.created.event";
    }

    @Override
    public String getType() {
        return "usernotification";
    }
}
