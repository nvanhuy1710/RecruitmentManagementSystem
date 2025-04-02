package com.app.homeworkoutapplication.module.account.service;

import com.app.homeworkoutapplication.module.account.dto.RegisterRequest;
import com.app.homeworkoutapplication.module.user.dto.User;
import org.springframework.web.multipart.MultipartFile;

public interface AccountService {

    User register(RegisterRequest registerRequest, String industrys);

    void activateUser(String email);

    User updateAccount(User user);

    void updateAvatar(MultipartFile file);

    void updatePassword(String newPass);

    void forgotPassword(String username);
}
