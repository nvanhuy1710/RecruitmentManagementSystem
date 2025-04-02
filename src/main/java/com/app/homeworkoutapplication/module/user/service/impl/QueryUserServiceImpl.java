package com.app.homeworkoutapplication.module.user.service.impl;

import com.app.homeworkoutapplication.entity.UserEntity;
import com.app.homeworkoutapplication.entity.UserEntity_;
import com.app.homeworkoutapplication.entity.mapper.UserMapper;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.module.user.service.criteria.UserCriteria;
import com.app.homeworkoutapplication.repository.UserRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryUserServiceImpl extends QueryService<UserEntity> implements QueryUserService {

    private final UserRepository userRepository;

    private final BlobStorageService blobStorageService;

    private final UserMapper userMapper;

    public QueryUserServiceImpl(UserRepository userRepository, BlobStorageService blobStorageService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.blobStorageService = blobStorageService;
        this.userMapper = userMapper;
    }

    public List<User> findListByCriteria(UserCriteria criteria) {
        return userRepository.findAll(createSpecification(criteria)).stream().map(userEntity -> {
            User user = userMapper.toDto(userEntity);
            getPublicUrl(user);
            return user;
        }).toList();
    }

    public Long count(UserCriteria criteria) {
        return userRepository.count(createSpecification(criteria));
    }

    public Page<User> findPageByCriteria(UserCriteria criteria, Pageable pageable) {
        Page<UserEntity> page =  userRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
            page.getContent().stream().map(userEntity -> {
                User user = userMapper.toDto(userEntity);
                getPublicUrl(user);
                return user;
            }).toList(),
            pageable,
            page.getTotalElements()
        );
    }

    public User findById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("Not found user by id " + id);
        }
        User user = userMapper.toDto(userEntity.get());
        getPublicUrl(user);
        return user;
    }

    public User getByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("Not found user by username: " + username);
        }
        User user = userMapper.toDto(userEntity.get());
        getPublicUrl(user);
        return user;
    }

    public User getByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("Not found user by email: " + email);
        }
        User user = userMapper.toDto(userEntity.get());
        getPublicUrl(user);
        return user;
    }


    private Specification<UserEntity> createSpecification(UserCriteria criteria) {
        Specification<UserEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), UserEntity_.id));
        }
        if (criteria.getEmail() != null) {
            specification = specification.and(buildStringSpecification(criteria.getEmail(), UserEntity_.email));
        }
        if (criteria.getFullName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getFullName(), UserEntity_.fullName));
        }
        if (criteria.getUsername() != null) {
            specification = specification.and(buildStringSpecification(criteria.getUsername(), UserEntity_.username));
        }
        if (criteria.getBirth() != null) {
            specification = specification.and(buildSpecification(criteria.getBirth(), UserEntity_.birth));
        }

        return specification;
    }

    private void getPublicUrl(User user) {
        if(user.getAvatarUrl() != null) {
            user.setPublicAvatarUrl(blobStorageService.getUrl(user.getAvatarUrl()));
        }
    }
}
