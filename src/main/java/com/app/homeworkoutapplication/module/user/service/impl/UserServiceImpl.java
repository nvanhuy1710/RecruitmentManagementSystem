package com.app.homeworkoutapplication.module.user.service.impl;

import com.app.homeworkoutapplication.entity.*;
import com.app.homeworkoutapplication.entity.mapper.UserMapper;
import com.app.homeworkoutapplication.module.company.dto.Company;
import com.app.homeworkoutapplication.module.role.dto.Role;
import com.app.homeworkoutapplication.module.role.service.QueryRoleService;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.module.user.service.UserService;
import com.app.homeworkoutapplication.repository.UserCompanyRepository;
import com.app.homeworkoutapplication.repository.UserRepository;
import com.app.homeworkoutapplication.repository.UserSkillRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final QueryUserService queryUserService;

    private final QueryRoleService queryRoleService;

    private final UserSkillRepository userSkillRepository;

    private final UserCompanyRepository userCompanyRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, QueryUserService queryUserService, QueryRoleService queryRoleService, UserSkillRepository userSkillRepository, UserCompanyRepository userCompanyRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.queryUserService = queryUserService;
        this.queryRoleService = queryRoleService;
        this.userSkillRepository = userSkillRepository;
        this.userCompanyRepository = userCompanyRepository;
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
            existUser.setLocked(false);
            saveSkill(existUser.getId(), user.getSkillIds());
            return userMapper.toDto(userRepository.save(userMapper.toEntity(existUser)));
        }
        else {
            validateUser(user);
        }

        User result = userMapper.toDto(userRepository.save(userMapper.toEntity(user)));
        saveSkill(result.getId(), user.getSkillIds());
        return result;
    }

    @Override
    public User updateRole(Long userId, String roleName) {
        Role role = queryRoleService.getByName(roleName);
        User user = queryUserService.findById(userId);
        user.setRoleId(role.getId());
        return userMapper.toDto(userRepository.save(userMapper.toEntity(user)));
    }

    @Override
    public void followCompany(Long userId, Long companyId) {
        Optional<UserCompanyEntity> userCompany = userCompanyRepository.findByUserIdAndCompanyId(userId, companyId);
        if(userCompany.isEmpty()) {
            UserCompanyEntity newData = new UserCompanyEntity();
            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);
            newData.setUser(userEntity);

            CompanyEntity company = new CompanyEntity();
            company.setId(companyId);
            newData.setCompany(company);

            userCompanyRepository.save(newData);
        }
    }

    @Override
    public void unFollowCompany(Long userId, Long companyId) {
        Optional<UserCompanyEntity> userCompany = userCompanyRepository.findByUserIdAndCompanyId(userId, companyId);
        userCompany.ifPresent(userCompanyEntity -> userCompanyRepository.deleteById(userCompanyEntity.getId()));
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

    @Override
    public void lockUser(Long userId) {
        User existUser = queryUserService.findById(userId);
        existUser.setLocked(true);
        userRepository.save(userMapper.toEntity(existUser));
    }

    @Override
    public void unLockUser(Long userId) {
        User existUser = queryUserService.findById(userId);
        existUser.setLocked(false);
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

    public void saveSkill(Long userId, List<Long> skillIds) {
        Set<Long> currentIds = userSkillRepository.findByUserId(userId)
                .stream()
                .map(entity -> entity.getSkill().getId())
                .collect(Collectors.toSet());

        Set<Long> newIds = new HashSet<>(skillIds);

        Set<Long> toAdds = new HashSet<>(newIds);
        toAdds.removeAll(currentIds);

        Set<Long> toRemoves = new HashSet<>(currentIds);
        toRemoves.removeAll(newIds);

        for(Long toAdd : toAdds) {
            UserSkillEntity entity = new UserSkillEntity();

            SkillEntity skill = new SkillEntity();
            skill.setId(toAdd);

            UserEntity userEntity = new UserEntity();
            userEntity.setId(userId);

            entity.setSkill(skill);
            entity.setUser(userEntity);

            userSkillRepository.save(entity);
        }

        for(Long toRemove : toRemoves) {
            userSkillRepository.deleteByUserIdAndSkillId(userId, toRemove);
        }
    }
}
