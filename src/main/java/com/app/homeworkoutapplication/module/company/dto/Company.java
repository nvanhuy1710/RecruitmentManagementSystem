package com.app.homeworkoutapplication.module.company.dto;

import lombok.Data;

@Data
public class Company {
    private Long id;

    private String name;

    private String imagePath;

    private String imageUrl;

    private String address;

    private String location;

    private String description;
}
