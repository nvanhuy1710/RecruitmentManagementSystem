package com.app.homeworkoutapplication.module.account.service.impl;

import com.app.homeworkoutapplication.entity.mapper.UserMapper;
import com.app.homeworkoutapplication.module.account.dto.RegisterRequest;
import com.app.homeworkoutapplication.module.account.service.AccountService;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.module.mail.service.MailService;
import com.app.homeworkoutapplication.module.industry.service.QueryIndustryService;
import com.app.homeworkoutapplication.module.role.dto.Role;
import com.app.homeworkoutapplication.module.role.service.QueryRoleService;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.module.user.service.UserService;
import com.app.homeworkoutapplication.util.BlobStoragePathUtil;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
    private static final int PASSWORD_LENGTH = 9;

    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final QueryIndustryService queryIndustryService;

    private final BlobStorageService blobStorageService;

    private final CurrentUserUtil currentUserUtil;

    private final UserService userService;

    private final QueryUserService queryUserService;

    private final QueryRoleService queryRoleService;

    private final BlobStoragePathUtil blobStoragePathUtil;

    public AccountServiceImpl(MailService mailService, PasswordEncoder passwordEncoder, UserMapper userMapper, QueryIndustryService queryIndustryService, BlobStorageService blobStorageService, CurrentUserUtil currentUserUtil, UserService userService, QueryUserService queryUserService, QueryRoleService queryRoleService, BlobStoragePathUtil blobStoragePathUtil) {
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.queryIndustryService = queryIndustryService;
        this.blobStorageService = blobStorageService;
        this.currentUserUtil = currentUserUtil;
        this.userService = userService;
        this.queryUserService = queryUserService;
        this.queryRoleService = queryRoleService;
        this.blobStoragePathUtil = blobStoragePathUtil;
    }

    @Override
    public User register(RegisterRequest registerRequest, String industrys) {

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User newUser = new User();
        Role role = queryRoleService.getByName("USER");
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setEmail(registerRequest.getEmail());
        newUser.setBirth(registerRequest.getBirth());
        newUser.setGender(registerRequest.getGender());
        newUser.setFullName(registerRequest.getFullName());
        newUser.setIsActivated(false);
        newUser.setRoleId(role.getId());
        newUser = userService.save(newUser);
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
            String path = blobStoragePathUtil.getAvatarPath(userOptional.getId(), file.getOriginalFilename());
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
