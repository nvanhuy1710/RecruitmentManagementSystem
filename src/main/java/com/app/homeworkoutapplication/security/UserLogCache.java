package com.app.homeworkoutapplication.security;

import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserLogCache {

    private final Map<String, User> userMapCache = new HashMap<>();

    private final QueryUserService queryUserService;

    public UserLogCache(QueryUserService queryUserService) {
        this.queryUserService = queryUserService;
    }
}
