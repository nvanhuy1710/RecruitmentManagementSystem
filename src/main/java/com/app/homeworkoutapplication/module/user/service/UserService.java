package com.app.homeworkoutapplication.module.user.service;

import com.app.homeworkoutapplication.module.user.dto.User;

public interface UserService {

    User save(User user);

    User updateRole(Long userId, String roleName);

    void updateAvatar(Long userId, String avatarPath);

    void updatePassword(Long userId, String newPass);

    void delete(Long id);
}
