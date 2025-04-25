package com.app.homeworkoutapplication.module.user.service.impl;

import com.app.homeworkoutapplication.entity.mapper.UserMapper;
import com.app.homeworkoutapplication.module.role.dto.Role;
import com.app.homeworkoutapplication.module.role.service.QueryRoleService;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.module.user.service.UserService;
import com.app.homeworkoutapplication.repository.UserRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final QueryUserService queryUserService;

    private final QueryRoleService queryRoleService;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, QueryUserService queryUserService, QueryRoleService queryRoleService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.queryUserService = queryUserService;
        this.queryRoleService = queryRoleService;
        this.userMapper = userMapper;
    }

    public User save(User user) {
        if (user.getId() != null) {
            try {
                User existUser = queryUserService.getByEmail(user.getEmail());
                if (!existUser.getId().equals(user.getId())) {
                    throw new BadRequestException("Email already exists: " + user.getEmail());
                }
            } catch (NotFoundException ignored) { }

            User existUser = queryUserService.findById(user.getId());
            existUser.setEmail(user.getEmail());
            existUser.setFullName(user.getFullName());
            existUser.setBirth(user.getBirth());
            existUser.setGender(user.getGender());
            return userMapper.toDto(userRepository.save(userMapper.toEntity(existUser)));
        }
        else {
            validateUser(user);
        }

        return userMapper.toDto(userRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public User updateRole(Long userId, String roleName) {
        Role role = queryRoleService.getByName(roleName);
        User user = queryUserService.findById(userId);
        user.setRoleId(role.getId());
        return userMapper.toDto(userRepository.save(userMapper.toEntity(user)));
    }

    public void updateAvatar(Long userId, String avatarPath) {
        User existUser = queryUserService.findById(userId);
        existUser.setAvatarPath(avatarPath);
        userRepository.save(userMapper.toEntity(existUser));
    }

    public void updatePassword(Long userId, String newPass) {
        User existUser = queryUserService.findById(userId);
        existUser.setPassword(newPass);
        userRepository.save(userMapper.toEntity(existUser));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private void validateUser(User user) {
        try {
            queryUserService.getByEmail(user.getEmail());
            throw new BadRequestException("Email already exists: " + user.getEmail());
        } catch (NotFoundException ignored) { }
        try {
            queryUserService.getByUsername(user.getUsername());
            throw new BadRequestException("Username already exists: " + user.getEmail());
        } catch (NotFoundException ignored) { }
    }
}
