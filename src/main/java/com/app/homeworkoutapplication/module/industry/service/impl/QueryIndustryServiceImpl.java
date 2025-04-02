package com.app.homeworkoutapplication.module.industry.service.impl;

import com.app.homeworkoutapplication.entity.IndustryEntity;
import com.app.homeworkoutapplication.entity.IndustryEntity_;
import com.app.homeworkoutapplication.entity.mapper.IndustryMapper;
import com.app.homeworkoutapplication.module.industry.dto.Industry;
import com.app.homeworkoutapplication.module.industry.service.QueryIndustryService;
import com.app.homeworkoutapplication.module.industry.service.criteria.IndustryCriteria;
import com.app.homeworkoutapplication.repository.IndustryRepository;
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
public class QueryIndustryServiceImpl extends QueryService<IndustryEntity> implements QueryIndustryService {

    private final IndustryRepository industryRepository;

    private final IndustryMapper industryMapper;

    public QueryIndustryServiceImpl(IndustryRepository industryRepository, IndustryMapper industryMapper) {
        this.industryRepository = industryRepository;
        this.industryMapper = industryMapper;
    }

    public List<Industry> findListByCriteria(IndustryCriteria criteria) {
        return industryRepository.findAll(createSpecification(criteria)).stream().map(industryMapper::toDto).toList();
    }

    public Page<Industry> findPageByCriteria(IndustryCriteria criteria, Pageable pageable) {
        Page<IndustryEntity> page =  industryRepository.findAll(createSpecification(criteria), pageable);
        return new PageImpl<>(
                page.getContent().stream().map(industryMapper::toDto).toList(),
                pageable,
                page.getTotalElements()
        );
    }

    public Industry getById(Long id) {
        Optional<IndustryEntity> industryEntity = industryRepository.findById(id);
        if (industryEntity.isEmpty()) {
            throw new NotFoundException("Not found industry by id " + id);
        }
        return industryMapper.toDto(industryEntity.get());
    }

    public Industry getByName(String name) {
        Optional<IndustryEntity> industryEntity = industryRepository.findByName(name);
        if (industryEntity.isEmpty()) {
            throw new NotFoundException("Not found industry by name " + name);
        }
        return industryMapper.toDto(industryEntity.get());
    }

    private Specification<IndustryEntity> createSpecification(IndustryCriteria criteria) {
        Specification<IndustryEntity> specification = Specification.where(null);

        if (criteria.getId() != null) {
            specification = specification.and(buildRangeSpecification(criteria.getId(), IndustryEntity_.id));
        }
        if (criteria.getName() != null) {
            specification = specification.and(buildStringSpecification(criteria.getName(), IndustryEntity_.name));
        }

        return specification;
    }
}
