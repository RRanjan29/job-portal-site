package com.example.jobportal.service;

import com.example.jobportal.dto.ApplicationDto;
import com.example.jobportal.entity.Application;
import com.example.jobportal.entity.ApplicationStatus;
import com.example.jobportal.entity.Job;
import com.example.jobportal.repository.ApplicationRepository;
import com.example.jobportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    public ApplicationDto applyForJob(Long userId, Long jobId, String resumePath) {
        if (applicationRepository.findByUserIdAndJobId(userId, jobId).isPresent()) {
            throw new RuntimeException("Already applied");
        }

        Application application = new Application();
        application.setUserId(userId);
        application.setJobId(jobId);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setResumePath(resumePath);
        applicationRepository.save(application);
        return convertToDto(application);
    }

    public List<ApplicationDto> getApplicationsByUser(Long userId) {
        List<Application> applications = applicationRepository.findByUserId(userId);
        return applications.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<ApplicationDto> getApplicationsByJob(Long jobId) {
        List<Application> applications = applicationRepository.findByJobId(jobId);
        return applications.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void updateApplicationStatus(Long id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
        application.setStatus(status);
        applicationRepository.save(application);
    }

    private ApplicationDto convertToDto(Application application) {
        ApplicationDto dto = new ApplicationDto();
        dto.setId(application.getId());
        dto.setJobId(application.getJobId());
        Job job = jobRepository.findById(application.getJobId()).orElse(null);
        if (job != null) {
            dto.setJobTitle(job.getTitle());
        }
        dto.setStatus(application.getStatus());
        dto.setResumePath(application.getResumePath());
        return dto;
    }

}