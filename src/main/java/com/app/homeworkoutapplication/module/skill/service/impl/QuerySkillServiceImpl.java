package com.app.homeworkoutapplication.module.skill.service.impl;

import com.app.homeworkoutapplication.entity.*;
import com.app.homeworkoutapplication.entity.mapper.SkillMapper;
import com.app.homeworkoutapplication.module.skill.dto.Skill;
import com.app.homeworkoutapplication.module.skill.service.QuerySkillService;
import com.app.homeworkoutapplication.module.skill.service.criteria.SkillCriteria;
import com.app.homeworkoutapplication.repository.SkillRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.NotFoundException;
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
public class QuerySkillServiceImpl extends QueryService<SkillEntity> implements QuerySkillService {

    private final SkillRepository skillRepository;

    private final SkillMapper skillMapper;


    public QuerySkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper) {
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    public List<Skill> findListByArticleId(Long articleId) {
        SkillCriteria criteria = new SkillCriteria();
        LongFilter id = new LongFilter();

        id.setEquals(articleId);

        criteria.setArticleId(id);

        return findListByCriteria(criteria);
    }

    public List<Skill> findListByCriteria(SkillCriteria criteria) {
        return skillRepository.findAll(createSpecification(criteria)).stream().map(skillMapper::toDto).toList();
    }

    public Page<Skill> findPageByCriteria(SkillCriteria criteria, Pageable pageable) {
        Page<SkillEntity> page =  skillRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(skillMapper::toDto).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    public Skill getById(Long id) {
        Optional<SkillEntity> skillEntity = skillRepository.findById(id);
        if (skillEntity.isEmpty()) {
            throw new NotFoundException("Not found skill by id " + id);
        }
        return skillMapper.toDto(skillEntity.get());
    }

    public Skill getByName(String name) {
        Optional<SkillEntity> skillEntity = skillRepository.findByName(name);
        if (skillEntity.isEmpty()) {
            throw new NotFoundException("Not found skill by name " + name);
        }
        return skillMapper.toDto(skillEntity.get());
    }


    private Specification<SkillEntity> createSpecification(SkillCriteria criteria) {
        Specification<SkillEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), SkillEntity_.id));
        }
        if (criteria.getName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getName(), SkillEntity_.name));
        }
        if (criteria.getUserId() != null) {
            specification = specification.and(
                buildSpecification(criteria.getUserId(), root ->
                    root.join(SkillEntity_.USER_SKILLS, JoinType.LEFT).join(UserSkillEntity_.USER, JoinType.LEFT).get(UserEntity_.ID))
            );
        }
        if (criteria.getUserId() != null) {
            specification = specification.and(
                    buildSpecification(criteria.getUserId(), root ->
                            root.join(SkillEntity_.USER_SKILLS, JoinType.LEFT).join(UserSkillEntity_.USER, JoinType.LEFT).get(UserEntity_.ID))
            );
        }
        if (criteria.getArticleId() != null) {
            specification = specification.and(
                    buildSpecification(criteria.getArticleId(), root ->
                            root.join(SkillEntity_.ARTICLE_SKILLS, JoinType.LEFT).join(ArticleSkillEntity_.ARTICLE, JoinType.LEFT).get(ArticleEntity_.ID))
            );
        }
        if (criteria.getName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getName(), SkillEntity_.name));
        }

        return specification;
    }
}
