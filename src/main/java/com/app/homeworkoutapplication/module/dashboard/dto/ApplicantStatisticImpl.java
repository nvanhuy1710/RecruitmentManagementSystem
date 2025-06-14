package com.app.homeworkoutapplication.module.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantStatisticImpl {

    private Long count;

    private String month;

    public ApplicantStatisticImpl(ApplicantStatistic applicantStatistic) {
        this.count = applicantStatistic.getCount();
        this.month = applicantStatistic.getMonth();
    }
}
