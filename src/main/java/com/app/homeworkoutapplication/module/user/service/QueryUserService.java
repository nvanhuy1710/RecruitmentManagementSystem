package com.app.homeworkoutapplication.module.user.service;

import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.criteria.UserCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryUserService {

    List<User> findListByCriteria(UserCriteria criteria);

    Long count(UserCriteria criteria);

    Page<User> findPageByCriteria(UserCriteria criteria, Pageable pageable);

    User findById(Long id);

    User getByUsername(String username);

    User getByEmail(String email);
}
