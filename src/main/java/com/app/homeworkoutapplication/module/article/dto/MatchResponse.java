package com.app.homeworkoutapplication.module.article.dto;

import lombok.Data;
import java.util.Map;

@Data
public class MatchResponse {
    private boolean success;
    private Map<Long, Double> results;
} 