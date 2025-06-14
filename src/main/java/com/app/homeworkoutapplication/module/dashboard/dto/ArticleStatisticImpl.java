package com.app.homeworkoutapplication.module.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleStatisticImpl {
    private Long count;

    private String month;

    public ArticleStatisticImpl(ArticleStatistic applicantStatistic) {
        this.count = applicantStatistic.getCount();
        this.month = applicantStatistic.getMonth();
    }
}
