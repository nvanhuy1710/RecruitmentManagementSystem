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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class CaculateApplicantServiceImpl implements CaculateApplicantService {

    private final QueryApplicantService queryApplicantService;

    private final QueryArticleService queryArticleService;

    private final QueryDocumentService queryDocumentService;

    private final ApplicantService applicantService;

    private String ALGORITHM = "AES";

    private String KEY = "1f4fhg42od2MdS34gg2ksoe8@ks45y6j";

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

        try {
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

            formData.add("job_description", article.getRequirement());

            for (Map.Entry<Long, String> entry : payload.entrySet()) {
                formData.add("cv_ids", entry.getKey().toString());
                formData.add("urls", entry.getValue());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-Key", encrypt("secret-api-key-match-score"));
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

    @Override
    public void caculateMatchScoreByApplicantId(Long applicantId) {
        Applicant applicant = queryApplicantService.getById(applicantId);

        Article article = queryArticleService.getById(applicant.getArticleId());

        if(article.getAutoCaculate() == null || ! article.getAutoCaculate()) {
            return;
        }

        List<Document> documents = queryDocumentService.findByApplicantId(applicantId);

        Map<Long, String> payload = new HashMap<>();

        for (Document document : documents) {
            payload.put(document.getId(), document.getFileUrl());
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

            formData.add("job_description", article.getRequirement());

            for (Map.Entry<Long, String> entry : payload.entrySet()) {
                formData.add("cv_ids", entry.getKey().toString());
                formData.add("urls", entry.getValue());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-Key", encrypt("secret-api-key-match-score"));
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

            ResponseEntity<MatchScore> response = restTemplate.postForEntity(
                    "http://localhost:5000/api/match-url", requestEntity, MatchScore.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                for(Map.Entry<Long, Double> result : response.getBody().getResults().entrySet()) {
                    if (applicant.getMatchScore() == null || applicant.getMatchScore() < result.getValue()) {
                        applicant.setMatchScore(result.getValue());
                    }
                }
                applicantService.updateScore(applicant.getId(), applicant.getMatchScore());
            } else {
                System.err.println("Match URL API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(1, keySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        String param = Base64.getEncoder().encodeToString(encryptedBytes);
        return URLEncoder.encode(param, StandardCharsets.UTF_8);
    }
}
