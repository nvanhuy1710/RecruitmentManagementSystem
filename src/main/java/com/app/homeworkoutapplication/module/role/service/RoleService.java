package com.app.homeworkoutapplication.module.role.service;

import com.app.homeworkoutapplication.module.role.dto.Role;

public interface RoleService {

    Role create(Role role);

    Role update(Role role);

    void delete(Long id);
}
