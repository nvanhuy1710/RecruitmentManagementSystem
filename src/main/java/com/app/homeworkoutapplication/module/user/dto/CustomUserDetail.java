package com.app.homeworkoutapplication.module.user.dto;

import com.app.homeworkoutapplication.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomUserDetail implements UserDetails {

    private final UserEntity userEntity;

    public CustomUserDetail(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authoritySet = new ArrayList<>();
//        for(IndustryEntity authority : user.getIndustrys()) {
//            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
//            authoritySet.add(grantedAuthority);
//        }
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userEntity.getArticle().getName());
        authoritySet.add(grantedAuthority);
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        return userEntity.getFullName();
    }
}
