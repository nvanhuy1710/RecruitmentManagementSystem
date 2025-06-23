package com.app.homeworkoutapplication.entity.enumeration;

public enum ScoreResponseKey {
    TOTAL_SCORE("TOTAL_SCORE","total_score"),
    ENTITY_SCORE("ENTITY_SCORE","entity_score"),
    SKILLS_SCORE("SKILLS_SCORE","skills_score"),
    EXPERIENCE_SCORE("EXPERIENCE_SCORE","experience_score"),
    EDUCATION_SCORE("EDUCATION_SCORE","education_score"),
    ;

    private final String name;

    private final String value;

    ScoreResponseKey(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
