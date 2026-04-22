package com.example.jobportal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String company;

    @NotBlank
    private String location;

    private Double salary;

}