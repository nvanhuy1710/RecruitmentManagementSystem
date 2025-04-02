package com.app.homeworkoutapplication.module.industry.service.impl;

import com.app.homeworkoutapplication.entity.mapper.IndustryMapper;
import com.app.homeworkoutapplication.module.industry.dto.Industry;
import com.app.homeworkoutapplication.module.industry.service.IndustryService;
import com.app.homeworkoutapplication.repository.IndustryRepository;
import com.app.homeworkoutapplication.web.rest.error.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class IndustryServiceImpl implements IndustryService {

    private final IndustryRepository industryRepository;

    private final IndustryMapper industryMapper;

    public IndustryServiceImpl(IndustryRepository industryRepository, IndustryMapper industryMapper) {
        this.industryRepository = industryRepository;
        this.industryMapper = industryMapper;
    }

    public Industry create(Industry industry) {
        if (industry.getId() != null) {
            throw new BadRequestException("id not null!");
        }
        return industryMapper.toDto(industryRepository.save(industryMapper.toEntity(industry)));
    }

    public Industry update(Industry industry) {
        if (industry.getId() == null) {
            throw new BadRequestException("id null!");
        }
        return industryMapper.toDto(industryRepository.save(industryMapper.toEntity(industry)));
    }

    public void delete(Long id) {
        industryRepository.deleteById(id);
    }
}
