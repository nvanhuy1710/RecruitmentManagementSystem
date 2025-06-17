package com.app.homeworkoutapplication.module.user.service.impl;

import com.app.homeworkoutapplication.entity.*;
import com.app.homeworkoutapplication.entity.mapper.CompanyMapper;
import com.app.homeworkoutapplication.entity.mapper.RoleMapper;
import com.app.homeworkoutapplication.entity.mapper.SkillMapper;
import com.app.homeworkoutapplication.entity.mapper.UserMapper;
import com.app.homeworkoutapplication.module.blobstorage.service.BlobStorageService;
import com.app.homeworkoutapplication.module.company.dto.Company;
import com.app.homeworkoutapplication.module.user.dto.User;
import com.app.homeworkoutapplication.module.user.service.QueryUserService;
import com.app.homeworkoutapplication.module.user.service.criteria.UserCriteria;
import com.app.homeworkoutapplication.repository.CompanyRepository;
import com.app.homeworkoutapplication.repository.UserCompanyRepository;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryUserServiceImpl extends QueryService<UserEntity> implements QueryUserService {

    private final UserRepository userRepository;

    private final CompanyRepository companyRepository;

    private final UserCompanyRepository userCompanyRepository;

    private final BlobStorageService blobStorageService;

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    private final CompanyMapper companyMapper;

    private final SkillMapper skillMapper;

    public QueryUserServiceImpl(UserRepository userRepository, CompanyRepository companyRepository, UserCompanyRepository userCompanyRepository, BlobStorageService blobStorageService, UserMapper userMapper, RoleMapper roleMapper, CompanyMapper companyMapper, SkillMapper skillMapper) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.userCompanyRepository = userCompanyRepository;
        this.blobStorageService = blobStorageService;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.companyMapper = companyMapper;
        this.skillMapper = skillMapper;
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

    @Override
    public List<User> findListFollowByCompanyId(Long id) {
        List<UserCompanyEntity> entities = userCompanyRepository.findByCompanyId(id);
        if(!entities.isEmpty()) {
            return userMapper.toDto(entities.stream().map(UserCompanyEntity::getUser).toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Company> getFollowedCompanies(Long userId) {
        List<UserCompanyEntity> entities = userCompanyRepository.findByUserId(userId);
        if(!entities.isEmpty()) {
            return companyMapper.toDto(entities.stream().map(UserCompanyEntity::getCompany).toList());
        }
        return Collections.emptyList();    }

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
        if (criteria.getLocked() != null) {
            specification = specification.and(buildSpecification(criteria.getLocked(), UserEntity_.locked));
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
        user.setSkillIds(entity.getUserSkills().stream().map(userSKill -> userSKill.getSkill().getId()).toList());
        user.setSkills(skillMapper.toDto(entity.getUserSkills().stream().map(UserSkillEntity::getSkill).toList()));
        user.setFollowCompanies(companyMapper.toDto(entity.getUserCompanies().stream().map(UserCompanyEntity::getCompany).toList()));
    }
}
