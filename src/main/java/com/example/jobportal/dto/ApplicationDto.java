package com.example.jobportal.dto;

import com.example.jobportal.entity.ApplicationStatus;
import lombok.Data;

@Data
public class ApplicationDto {

    private Long id;

    private Long jobId;

    private String jobTitle;

    private ApplicationStatus status;

    private String resumePath;

}