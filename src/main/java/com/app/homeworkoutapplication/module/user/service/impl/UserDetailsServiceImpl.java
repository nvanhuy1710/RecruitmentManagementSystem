package com.app.homeworkoutapplication.module.user.service.impl;

import com.app.homeworkoutapplication.entity.UserEntity;
import com.app.homeworkoutapplication.module.user.dto.CustomUserDetail;
import com.app.homeworkoutapplication.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        if (!user.get().getIsActivated()) {
            throw new RuntimeException("User not activated: " + username);
        }
        return new CustomUserDetail(user.get());
    }
}

