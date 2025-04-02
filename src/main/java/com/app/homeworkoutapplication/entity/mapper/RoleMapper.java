package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.RoleEntity;
import com.app.homeworkoutapplication.module.role.dto.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface RoleMapper extends EntityMapper<Role, RoleEntity> {
}
