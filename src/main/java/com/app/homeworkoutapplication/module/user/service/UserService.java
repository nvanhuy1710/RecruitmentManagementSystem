package com.app.homeworkoutapplication.module.user.service;

import com.app.homeworkoutapplication.module.user.dto.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User updateRole(Long userId, String roleName);

    void followCompany(Long userId, Long companyId);

    void unFollowCompany(Long userId, Long companyId);

    void updateAvatar(Long userId, String avatarPath);

    void updatePassword(Long userId, String newPass);

    void lockUser(Long userId);

    void unLockUser(Long userId);

    void delete(Long id);
}
