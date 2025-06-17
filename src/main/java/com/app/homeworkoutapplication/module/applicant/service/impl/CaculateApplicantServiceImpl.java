package com.app.homeworkoutapplication.module.applicant.service.impl;

import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicant.dto.reponse.MatchScore;
import com.app.homeworkoutapplication.module.applicant.service.ApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.CaculateApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.QueryApplicantService;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.service.QueryArticleService;
import com.app.homeworkoutapplication.module.document.dto.Document;
import com.app.homeworkoutapplication.module.document.service.QueryDocumentService;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CaculateApplicantServiceImpl implements CaculateApplicantService {

    private final QueryApplicantService queryApplicantService;

    private final QueryArticleService queryArticleService;

    private final QueryDocumentService queryDocumentService;

    private final ApplicantService applicantService;

    public CaculateApplicantServiceImpl(QueryApplicantService queryApplicantService, QueryArticleService queryArticleService, QueryDocumentService queryDocumentService, ApplicantService applicantService) {
        this.queryApplicantService = queryApplicantService;
        this.queryArticleService = queryArticleService;
        this.queryDocumentService = queryDocumentService;
        this.applicantService = applicantService;
    }

    @Override
    public void caculateMatchScore(Long articleId) {
        List<Applicant> applicants = queryApplicantService.findListByArticleId(articleId);

        List<Document> documents = new ArrayList<>();

        for (Applicant applicant : applicants) {
            documents.addAll(applicant.getDocuments());
        }

        Article article = queryArticleService.getById(articleId);

        Map<Long, String> payload = new HashMap<>();

        for (Document document : documents) {
            payload.put(document.getId(), document.getFileUrl());
        }

        // Gọi API
        try {
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

            // Thêm job_description
            formData.add("job_description", article.getRequirement());

            // Thêm các urls và cv_ids
            for (Map.Entry<Long, String> entry : payload.entrySet()) {
                formData.add("cv_ids", entry.getKey().toString());
                formData.add("urls", entry.getValue());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-Key", "secret-api-key-match-score");
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

            ResponseEntity<MatchScore> response = restTemplate.postForEntity(
                    "http://localhost:5000/api/match-url", requestEntity, MatchScore.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                for(Map.Entry<Long, Double> result : response.getBody().getResults().entrySet()) {
                    Document document = documents.stream().filter(document1 -> document1.getId().equals(result.getKey())).toList().get(0);
                    Applicant applicant = applicants.stream().filter(applicant1 -> applicant1.getId().equals(document.getApplicantId())).toList().get(0);
                    if (applicant.getMatchScore() == null || applicant.getMatchScore() < result.getValue()) {
                        applicant.setMatchScore(result.getValue());
                    }
                }
                for (Applicant applicant : applicants) {
                    applicantService.updateScore(applicant.getId(), applicant.getMatchScore());
                }
            } else {
                System.err.println("Match URL API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
