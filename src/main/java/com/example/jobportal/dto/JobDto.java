package com.example.jobportal.dto;

import lombok.Data;

@Data
public class JobDto {

    private Long id;

    private String title;

    private String description;

    private String company;

    private String location;

    private Double salary;

}