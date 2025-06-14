package com.app.homeworkoutapplication.module.dashboard.controller;

import com.app.homeworkoutapplication.module.dashboard.dto.*;
import com.app.homeworkoutapplication.module.dashboard.service.QueryDashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "Authentication")
public class DashboardController {

    private final QueryDashboardService queryDashboardService;

    public DashboardController(QueryDashboardService queryDashboardService) {
        this.queryDashboardService = queryDashboardService;
    }

    @GetMapping("/count/article-date/{year}")
    public ResponseEntity<List<ArticleStatisticImpl>> count(@PathVariable int year) throws URISyntaxException {
        return ResponseEntity.ok(queryDashboardService.statisticArticles(year));
    }

    @GetMapping("/count/applicant-date/{year}")
    public ResponseEntity<List<ApplicantStatisticImpl>> countMore(@PathVariable int year) throws URISyntaxException {
        return ResponseEntity.ok(queryDashboardService.statisticApplicants(year));
    }

    @GetMapping("/count/article-company/{year}")
    public ResponseEntity<List<ArticleCompanyStatisticImpl>> countMoreMore(@PathVariable int year) throws URISyntaxException {
        return ResponseEntity.ok(queryDashboardService.statisticArticleCompanies(year));
    }
}
