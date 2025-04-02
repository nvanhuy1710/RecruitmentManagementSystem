package com.app.homeworkoutapplication.module.industry.service;

import com.app.homeworkoutapplication.module.industry.dto.Industry;

public interface IndustryService {

    Industry create(Industry industry);

    Industry update(Industry industry);

    void delete(Long id);
}
