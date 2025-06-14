package com.app.homeworkoutapplication.module.dashboard.service.impl;

import com.app.homeworkoutapplication.module.dashboard.dto.*;
import com.app.homeworkoutapplication.module.dashboard.service.QueryDashboardService;
import com.app.homeworkoutapplication.repository.ApplicantRepository;
import com.app.homeworkoutapplication.repository.ArticleRepository;
import com.app.homeworkoutapplication.util.CurrentUserUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class QueryDashboardServiceImpl implements QueryDashboardService {

    private final ApplicantRepository applicantRepository;

    private final ArticleRepository articleRepository;

    private final CurrentUserUtil currentUserUtil;

    public QueryDashboardServiceImpl(ApplicantRepository applicantRepository, ArticleRepository articleRepository, CurrentUserUtil currentUserUtil) {
        this.applicantRepository = applicantRepository;
        this.articleRepository = articleRepository;
        this.currentUserUtil = currentUserUtil;
    }

    @Override
    public List<ApplicantStatisticImpl> statisticApplicants(int year) {
        return applicantRepository.countByMonthInYear(year, currentUserUtil.getCurrentUser().getId().intValue()).stream().map(ApplicantStatisticImpl::new).toList();
    }

    @Override
    public List<ArticleStatisticImpl> statisticArticles(int year) {
        return articleRepository.countByMonthInYear(year, currentUserUtil.getCurrentUser().getId().intValue()).stream().map(ArticleStatisticImpl::new).toList();
    }

    @Override
    public List<ArticleCompanyStatisticImpl> statisticArticleCompanies(int year) {
        return articleRepository.countByCompanyInYear(year, currentUserUtil.getCurrentUser().getId().intValue()).stream().map(ArticleCompanyStatisticImpl::new).toList();
    }
}
