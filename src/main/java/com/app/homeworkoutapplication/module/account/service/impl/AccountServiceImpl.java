package com.app.homeworkoutapplication.module.account.service.impl;

import com.app.homeworkoutapplication.entity.mapper.UserMapper;
import com.app.homeworkoutapplication.module.account.dto.RegisterRequest;
import com.app.homeworkoutapplication.module.account.service.AccountService;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.module.mail.service.MailService;
import com.app.homeworkoutapplication.module.role.service.QueryRoleService;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.module.user.service.UserService;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
    private static final int PASSWORD_LENGTH = 9;

    private String AVATAR_PATH = "homeworkoutapplication/user/{id}/avatar/";

    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final QueryRoleService queryRoleService;

    private final BlobStorageService blobStorageService;

    private final CurrentUserUtil currentUserUtil;

    private final UserService userService;

    private final QueryUserService queryUserService;

    public AccountServiceImpl(MailService mailService, PasswordEncoder passwordEncoder, UserMapper userMapper, QueryRoleService queryRoleService, BlobStorageService blobStorageService, CurrentUserUtil currentUserUtil, UserService userService, QueryUserService queryUserService) {
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.queryRoleService = queryRoleService;
        this.blobStorageService = blobStorageService;
        this.currentUserUtil = currentUserUtil;
        this.userService = userService;
        this.queryUserService = queryUserService;
    }

    @Override
    public User register(RegisterRequest registerRequest, String roles) {

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setRoleId(queryRoleService.getByName(roles).getId());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setIsActivated(false);
        newUser = userService.save(newUser);
//        userService.save(newUser)
        mailService.sendActivationEmail(newUser);
        return newUser;
    }

    @Override
    public void activateUser(String email) {
        User userOptional = queryUserService.getByEmail(email);
        if (userOptional != null) {
            userOptional.setIsActivated(true);
            userService.save(userOptional);
        }
    }

    @Override
    public User updateAccount(User user) {
        Long id = currentUserUtil.getCurrentUser().getId();
        user.setId(id);
        return userService.save(user);
    }

    @Override
    public void updateAvatar(MultipartFile file) {
        User userOptional = queryUserService.findById(currentUserUtil.getCurrentUser().getId());
        if (userOptional != null) {
            Map<String, String> values = new HashMap<>();
            values.put("id", userOptional.getId().toString());
            String path = StringSubstitutor.replace(AVATAR_PATH, values, "{", "}") + file.getOriginalFilename();
            blobStorageService.save(file, path);
            userService.updateAvatar(userOptional.getId(), path);
        }
    }

    @Override
    public void updatePassword(String newPass) {
        User userOptional = queryUserService.findById(currentUserUtil.getCurrentUser().getId());
        String encodedPassword = passwordEncoder.encode(newPass);
        userService.updatePassword(userOptional.getId(), encodedPassword);
    }

    @Override
    public void forgotPassword(String username) {
        User userOptional = queryUserService.getByUsername(username);
        String newPass = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(newPass);
        userService.updatePassword(userOptional.getId(), encodedPassword);
        mailService.sendForgotPassEmail(userOptional, newPass);
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}
