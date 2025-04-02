package com.app.homeworkoutapplication.module.mail.service.impl;

import com.app.homeworkoutapplication.module.mail.service.MailService;
import com.app.homeworkoutapplication.module.user.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${app.activation.url}")
    private String activationUrl;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationEmail(User user) {
        String subject = "Account Activation";
        String activationLink = activationUrl + "?email=" + user.getEmail();
        String message = "Click on the link to activate your account: " + activationLink;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }

    @Override
    public void sendForgotPassEmail(User user, String newPass) {
        String subject = "Forgot Password";
        String message = "This is your new password, please don't share to anyone: " + newPass;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}
