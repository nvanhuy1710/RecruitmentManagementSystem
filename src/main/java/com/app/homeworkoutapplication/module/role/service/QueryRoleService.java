package com.app.homeworkoutapplication.module.role.service;

import com.app.homeworkoutapplication.module.role.dto.Role;
import com.app.homeworkoutapplication.module.role.service.criteria.RoleCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryRoleService {

    List<Role> findListByCriteria(RoleCriteria criteria);

    Page<Role> findPageByCriteria(RoleCriteria criteria, Pageable pageable);

    Role getById(Long id);

    Role getByName(String name);
}
