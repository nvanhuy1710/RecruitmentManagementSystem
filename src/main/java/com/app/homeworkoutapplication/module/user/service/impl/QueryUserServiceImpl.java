package com.app.homeworkoutapplication.module.user.service.impl;

import com.app.homeworkoutapplication.entity.RoleEntity;
import com.app.homeworkoutapplication.entity.RoleEntity_;
import com.app.homeworkoutapplication.entity.UserEntity;
import com.app.homeworkoutapplication.entity.UserEntity_;
import com.app.homeworkoutapplication.entity.mapper.RoleMapper;
import com.app.homeworkoutapplication.entity.mapper.UserMapper;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.module.user.service.criteria.UserCriteria;
import com.app.homeworkoutapplication.repository.UserRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import jakarta.persistence.criteria.Join;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.BooleanFilter;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryUserServiceImpl extends QueryService<UserEntity> implements QueryUserService {

    private final UserRepository userRepository;

    private final BlobStorageService blobStorageService;

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    public QueryUserServiceImpl(UserRepository userRepository, BlobStorageService blobStorageService, UserMapper userMapper, RoleMapper roleMapper) {
        this.userRepository = userRepository;
        this.blobStorageService = blobStorageService;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    public List<User> findListByCriteria(UserCriteria criteria) {
        return userRepository.findAll(createSpecification(criteria)).stream().map(userEntity -> {
            User user = userMapper.toDto(userEntity);
            fetchData(userEntity, user);
            return user;
        }).toList();
    }

    @Override
    public List<User> findListEmployee() {
        UserCriteria criteria = new UserCriteria();

        BooleanFilter filter = new BooleanFilter();
        filter.setEquals(true);

        criteria.setEmployee(filter);
        return findListByCriteria(criteria);
    }

    public Long count(UserCriteria criteria) {
        return userRepository.count(createSpecification(criteria));
    }

    public Page<User> findPageByCriteria(UserCriteria criteria, Pageable pageable) {
        Page<UserEntity> page =  userRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
            page.getContent().stream().map(userEntity -> {
                User user = userMapper.toDto(userEntity);
                fetchData(userEntity, user);
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
        fetchData(userEntity.get(), user);
        return user;
    }

    public User getByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("Not found user by username: " + username);
        }
        User user = userMapper.toDto(userEntity.get());
        fetchData(userEntity.get(), user);
        return user;
    }

    public User getByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("Not found user by email: " + email);
        }
        User user = userMapper.toDto(userEntity.get());
        fetchData(userEntity.get(), user);
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
        if (criteria.getEmployee() != null) {
            specification = specification.and(specFindEmployee(criteria.getEmployee()));
        }

        return specification;
    }

    private Specification<UserEntity> specFindEmployee(BooleanFilter filter) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (filter.getEquals() != null) {
                Join<UserEntity, RoleEntity> joinTask = root.join(UserEntity_.role);
                return criteriaBuilder.equal(
                        joinTask.get(RoleEntity_.name),
                        "EMPLOYER"
                );
            }
            return null;
        };
    }

    private void fetchData(UserEntity entity, User user) {
        if(user.getAvatarPath() != null) {
            user.setAvatarUrl(blobStorageService.getUrl(user.getAvatarPath()));
        }
        user.setRoleName(entity.getRole().getName());
    }
}
