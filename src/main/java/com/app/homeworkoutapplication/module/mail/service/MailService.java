package com.app.homeworkoutapplication.module.mail.service;

import com.app.homeworkoutapplication.entity.enumeration.ApplicantStatus;
import com.app.homeworkoutapplication.module.user.dto.User;

public interface MailService {

    void sendActivationEmail(User user);

    void sendReviewApplicantResult(User user, ApplicantStatus status);

    void sendForgotPassEmail(User user, String newPass);
}
