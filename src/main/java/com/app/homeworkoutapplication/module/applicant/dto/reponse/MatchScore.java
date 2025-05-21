package com.app.homeworkoutapplication.module.applicant.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchScore {
    private Boolean success;

    private Map<Long, Double> results;
}
