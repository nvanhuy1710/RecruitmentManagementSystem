package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.UserEntity;
import com.app.homeworkoutapplication.module.user.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface UserMapper extends EntityMapper<User, UserEntity> {

    @Mapping(target = "role.id", source = "roleId")
    UserEntity toEntity(User user);

    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "role", ignore = true)
    User toDto(UserEntity user);
}
