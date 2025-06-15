package com.app.homeworkoutapplication.module.company.dto;

import com.app.homeworkoutapplication.entity.enumeration.CompanyStatus;
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

    private CompanyStatus status;
}
