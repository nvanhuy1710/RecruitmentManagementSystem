package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.UserNotificationEntity;
import com.app.homeworkoutapplication.module.usernotification.dto.UserNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserNotificationMapper extends EntityMapper<UserNotification, UserNotificationEntity> {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "user", ignore = true)
    UserNotification toDto(UserNotificationEntity entity);

    @Mapping(target = "user.id", source = "userId")
    UserNotificationEntity toEntity(UserNotification dto);
}