package com.app.homeworkoutapplication.module.applicant.service.impl;

import com.app.homeworkoutapplication.entity.enumeration.ScoreResponseKey;
import com.app.homeworkoutapplication.module.applicant.dto.Applicant;
import com.app.homeworkoutapplication.module.applicant.dto.reponse.MatchScore;
import com.app.homeworkoutapplication.module.applicant.service.ApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.CaculateApplicantService;
import com.app.homeworkoutapplication.module.applicant.service.QueryApplicantService;
import com.app.homeworkoutapplication.module.applicantscore.dto.ApplicantScore;
import com.app.homeworkoutapplication.module.applicantscore.service.ApplicantScoreService;
import com.app.homeworkoutapplication.module.article.dto.Article;
import com.app.homeworkoutapplication.module.article.service.QueryArticleService;
import com.app.homeworkoutapplication.module.document.dto.Document;
import com.app.homeworkoutapplication.module.document.service.QueryDocumentService;
import com.app.homeworkoutapplication.module.skill.dto.Skill;
import com.app.homeworkoutapplication.module.skill.service.QuerySkillService;
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

    private final ApplicantScoreService applicantScoreService;

    private final QuerySkillService querySkillService;

    private String ALGORITHM = "AES";

    private String KEY = "1f4fhg42od2MdS34gg2ksoe8@ks45y6j";

    public CaculateApplicantServiceImpl(QueryApplicantService queryApplicantService, QueryArticleService queryArticleService, QueryDocumentService queryDocumentService, ApplicantService applicantService, ApplicantScoreService applicantScoreService, QuerySkillService querySkillService) {
        this.queryApplicantService = queryApplicantService;
        this.queryArticleService = queryArticleService;
        this.queryDocumentService = queryDocumentService;
        this.applicantService = applicantService;
        this.applicantScoreService = applicantScoreService;
        this.querySkillService = querySkillService;
    }

    @Override
    public void caculateMatchScore(Long articleId) {
//        List<Applicant> applicants = queryApplicantService.findListByArticleId(articleId);
//
//        Article article = queryArticleService.getById(articleId);
//
//        try {
//            RestTemplate restTemplate = new RestTemplate();
//            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
//
//            formData.add("cv_url", documents.get(0).getFileUrl());
//            formData.add("job_description", article.getContent());
//            formData.add("job_experience", article.getRequirement());
//            formData.add("job_skills", buildSkillString(article.getId()));
//            formData.add("job_education", article.getEducationRequired());
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("X-API-Key", encrypt("secret-api-key-match-score"));
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);
//
//            for (Applicant applicant : applicants) {
//
//            }
//
//            ResponseEntity<MatchScore> response = restTemplate.postForEntity(
//                    "http://localhost:5000/api/match-url", requestEntity, MatchScore.class
//            );
//
//            if (response.getStatusCode().is2xxSuccessful()) {
//                for(Map.Entry<Long, Double> result : response.getBody().getResults().entrySet()) {
//                    Document document = documents.stream().filter(document1 -> document1.getId().equals(result.getKey())).toList().get(0);
//                    Applicant applicant = applicants.stream().filter(applicant1 -> applicant1.getId().equals(document.getApplicantId())).toList().get(0);
//                    if (applicant.getMatchScore() == null || applicant.getMatchScore() < result.getValue()) {
//                        applicant.setMatchScore(result.getValue());
//                    }
//                }
//                for (Applicant applicant : applicants) {
//                    applicantService.updateScore(applicant.getId(), applicant.getMatchScore());
//                }
//            } else {
//                System.err.println("Match URL API call failed with status: " + response.getStatusCode());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void caculateMatchScoreByApplicantId(Long applicantId) {
        Applicant applicant = queryApplicantService.getById(applicantId);

        Article article = queryArticleService.getById(applicant.getArticleId());

        if(article.getAutoCaculate() == null || ! article.getAutoCaculate()) {
            return;
        }

        List<Document> documents = queryDocumentService.findByApplicantId(applicantId);

        try {
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();

            formData.add("cv_url", documents.get(0).getFileUrl());
            formData.add("job_description", article.getContent());
            formData.add("job_experience", article.getRequirement());
            formData.add("job_skills", buildSkillString(article.getId()));
            formData.add("job_education", article.getEducationRequired());

            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-Key", encrypt("secret-api-key-match-score"));
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(formData, headers);

            ResponseEntity<MatchScore> response = restTemplate.postForEntity(
                    "http://localhost:5000/api/analyze-resume-from-url", requestEntity, MatchScore.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                for(Map.Entry<String, Double> result : response.getBody().getScores().entrySet()) {
                    if (applicant.getMatchScore() == null || applicant.getMatchScore() < result.getValue()) {
                        applicant.setMatchScore(result.getValue());
                    }
                }

                ApplicantScore applicantScore = new ApplicantScore();
                applicantScore.setOverall(response.getBody().getScores().get(ScoreResponseKey.TOTAL_SCORE.getValue()));
                applicantScore.setStructure(response.getBody().getScores().get(ScoreResponseKey.ENTITY_SCORE.getValue()));
                applicantScore.setExperience(response.getBody().getScores().get(ScoreResponseKey.EXPERIENCE_SCORE.getValue()));
                applicantScore.setEducation(response.getBody().getScores().get(ScoreResponseKey.EDUCATION_SCORE.getValue()));
                applicantScore.setSkill(response.getBody().getScores().get(ScoreResponseKey.SKILLS_SCORE.getValue()));
                applicantScore.setApplicantId(applicant.getId());

                applicantScoreService.create(applicantScore);
                applicantService.updateScore(applicant.getId(), applicantScore.getOverall());
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

    private String buildSkillString(Long articleId) {
        List<Skill> skills = querySkillService.findListByArticleId(articleId);
        if(!skills.isEmpty()) {
            List<String> names = skills.stream().map(Skill::getName).toList();
            return String.join(", ", names);
        }
        return "";
    }
}
