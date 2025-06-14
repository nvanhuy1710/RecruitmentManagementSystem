package com.app.homeworkoutapplication.module.dashboard.service;

import com.app.homeworkoutapplication.module.dashboard.dto.*;

import java.util.List;

public interface QueryDashboardService {

    List<ApplicantStatisticImpl> statisticApplicants(int year);

    List<ArticleStatisticImpl> statisticArticles(int year);

    List<ArticleCompanyStatisticImpl> statisticArticleCompanies(int year);

}
