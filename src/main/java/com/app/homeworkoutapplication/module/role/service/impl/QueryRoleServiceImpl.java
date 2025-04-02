package com.app.homeworkoutapplication.module.role.service.impl;

import com.app.homeworkoutapplication.entity.RoleEntity;
import com.app.homeworkoutapplication.entity.RoleEntity_;
import com.app.homeworkoutapplication.entity.mapper.RoleMapper;
import com.app.homeworkoutapplication.module.role.dto.Role;
import com.app.homeworkoutapplication.module.role.service.QueryRoleService;
import com.app.homeworkoutapplication.module.role.service.criteria.RoleCriteria;
import com.app.homeworkoutapplication.repository.RoleRepository;
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
public class QueryRoleServiceImpl extends QueryService<RoleEntity> implements QueryRoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;


    public QueryRoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public List<Role> findListByCriteria(RoleCriteria criteria) {
        return roleRepository.findAll(createSpecification(criteria)).stream().map(roleMapper::toDto).toList();
    }

    public Page<Role> findPageByCriteria(RoleCriteria criteria, Pageable pageable) {
        Page<RoleEntity> page =  roleRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(roleMapper::toDto).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    public Role getById(Long id) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(id);
        if (roleEntity.isEmpty()) {
            throw new NotFoundException("Not found role by id " + id);
        }
        return roleMapper.toDto(roleEntity.get());
    }

    public Role getByName(String name) {
        Optional<RoleEntity> roleEntity = roleRepository.findByName(name);
        if (roleEntity.isEmpty()) {
            throw new NotFoundException("Not found role by name " + name);
        }
        return roleMapper.toDto(roleEntity.get());
    }


    private Specification<RoleEntity> createSpecification(RoleCriteria criteria) {
        Specification<RoleEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), RoleEntity_.id));
        }
        if (criteria.getName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getName(), RoleEntity_.name));
        }

        return specification;
    }
}
