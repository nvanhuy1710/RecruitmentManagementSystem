package com.app.homeworkoutapplication.module.mail.service;

import com.app.homeworkoutapplication.module.user.dto.User;

public interface MailService {

    void sendActivationEmail(User user);

    void sendForgotPassEmail(User user, String newPass);
}
