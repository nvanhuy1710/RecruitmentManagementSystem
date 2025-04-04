package com.app.homeworkoutapplication.module.user.listener;

import com.app.homeworkoutapplication.entity.IndustryEntity;
import com.app.homeworkoutapplication.entity.RoleEntity;
import com.app.homeworkoutapplication.entity.UserEntity;
import com.app.homeworkoutapplication.entity.mapper.UserMapper;
import com.app.homeworkoutapplication.module.industry.service.QueryIndustryService;
import com.app.homeworkoutapplication.module.role.service.QueryRoleService;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.repository.UserRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ConfigDefaultUser {

    private final QueryUserService queryUserService;

    private final PasswordEncoder passwordEncoder;

    private final QueryRoleService queryRoleService;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public ConfigDefaultUser(QueryUserService queryUserService, PasswordEncoder passwordEncoder, QueryRoleService queryRoleService, UserMapper userMapper, UserRepository userRepository) {
        this.queryUserService = queryUserService;
        this.passwordEncoder = passwordEncoder;
        this.queryRoleService = queryRoleService;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultUser() {
        try {
            queryUserService.getByUsername("admin");
        } catch (NotFoundException e) {

            String encodedPassword = passwordEncoder.encode("Admin123@");

            UserEntity newUser = new UserEntity();
            newUser.setUsername("admin");
            newUser.setPassword(encodedPassword);
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setId(queryRoleService.getByName("ADMIN").getId());
            newUser.setRole(roleEntity);
            newUser.setEmail("admin@gmail.com");
            newUser.setIsActivated(true);
            userRepository.save(newUser);
        }
    }
}
