package com.app.homeworkoutapplication.module.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCompanyStatisticImpl {

    private String companyName;

    private Long count;

    public ArticleCompanyStatisticImpl(ArticleCompanyStatistic applicantStatistic) {
        this.count = applicantStatistic.getCount();
        this.companyName = applicantStatistic.getCompanyName();
    }
}
