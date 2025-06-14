package com.app.homeworkoutapplication.entity.mapper;

import com.app.homeworkoutapplication.entity.CompanyEntity;
import com.app.homeworkoutapplication.module.company.dto.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CompanyMapper extends EntityMapper<Company, CompanyEntity> {
}
