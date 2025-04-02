package com.app.homeworkoutapplication.module.userskill.service.impl;

import com.app.homeworkoutapplication.entity.*;
import com.app.homeworkoutapplication.entity.mapper.UserSkillMapper;
import com.app.homeworkoutapplication.module.userskill.dto.UserSkill;
import com.app.homeworkoutapplication.module.userskill.service.QueryUserSkillService;
import com.app.homeworkoutapplication.module.userskill.service.criteria.UserSkillCriteria;
import com.app.homeworkoutapplication.repository.UserSkillRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryUserSkillServiceImpl extends QueryService<UserSkillEntity> implements QueryUserSkillService {

    private final UserSkillRepository userSkillRepository;

    private final UserSkillMapper userSkillMapper;


    public QueryUserSkillServiceImpl(UserSkillRepository userSkillRepository, UserSkillMapper userSkillMapper) {
        this.userSkillRepository = userSkillRepository;
        this.userSkillMapper = userSkillMapper;
    }

    public List<UserSkill> findListByCriteria(UserSkillCriteria criteria) {
        return userSkillRepository.findAll(createSpecification(criteria)).stream().map(userSkillMapper::toDto).toList();
    }

    public Page<UserSkill> findPageByCriteria(UserSkillCriteria criteria, Pageable pageable) {
        Page<UserSkillEntity> page =  userSkillRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(userSkillMapper::toDto).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    public UserSkill getById(Long id) {
        Optional<UserSkillEntity> user_skillEntity = userSkillRepository.findById(id);
        if (user_skillEntity.isEmpty()) {
            throw new NotFoundException("Not found user_skill by id " + id);
        }
        return userSkillMapper.toDto(user_skillEntity.get());
    }

    private Specification<UserSkillEntity> createSpecification(UserSkillCriteria criteria) {
        Specification<UserSkillEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), UserSkillEntity_.id));
        }
        if (criteria.getUserId() != null) {
            specification = specification.and(specFindByUserId(criteria.getUserId()));
        }
        if (criteria.getSkillId() != null) {
            specification = specification.and(specFindBySkillId(criteria.getSkillId()));
        }

        return specification;
    }

    private Specification<UserSkillEntity> specFindByUserId(LongFilter idFilter) {
        if (idFilter.getEquals() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<UserSkillEntity, UserEntity> join = root.join(UserSkillEntity_.user, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(UserEntity_.id), idFilter.getEquals());
            };
        }
        if (idFilter.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<UserSkillEntity, UserEntity> join = root.join(UserSkillEntity_.user, JoinType.LEFT);
                return join.get(UserEntity_.id).in(idFilter.getIn());
            };
        }
        return null;
    }

    private Specification<UserSkillEntity> specFindBySkillId(LongFilter idFilter) {
        if (idFilter.getEquals() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<UserSkillEntity, SkillEntity> join = root.join(UserSkillEntity_.skill, JoinType.LEFT);
                return criteriaBuilder.equal(join.get(SkillEntity_.id), idFilter.getEquals());
            };
        }
        if (idFilter.getIn() != null) {
            return (root, query, criteriaBuilder) -> {
                Join<UserSkillEntity, SkillEntity> join = root.join(UserSkillEntity_.skill, JoinType.LEFT);
                return join.get(SkillEntity_.id).in(idFilter.getIn());
            };
        }
        return null;
    }
}
