package com.example.jobportal.service;

import com.example.jobportal.dto.JobCreateRequest;
import com.example.jobportal.dto.JobDto;
import com.example.jobportal.entity.Job;
import com.example.jobportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Page<JobDto> getAllJobs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> jobs = jobRepository.findAll(pageable);
        return jobs.map(this::convertToDto);
    }

    public JobDto getJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        return convertToDto(job);
    }

    public JobDto createJob(JobCreateRequest request, Long recruiterId) {
        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setRecruiterId(recruiterId);
        jobRepository.save(job);
        return convertToDto(job);
    }

    public JobDto updateJob(Long id, JobCreateRequest request, Long recruiterId) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        if (!job.getRecruiterId().equals(recruiterId)) {
            throw new RuntimeException("Unauthorized");
        }
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        jobRepository.save(job);
        return convertToDto(job);
    }

    public void deleteJob(Long id, Long recruiterId) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new RuntimeException("Job not found"));
        if (!job.getRecruiterId().equals(recruiterId)) {
            throw new RuntimeException("Unauthorized");
        }
        jobRepository.delete(job);
    }

    public List<JobDto> getJobsByRecruiter(Long recruiterId) {
        List<Job> jobs = jobRepository.findByRecruiterId(recruiterId);
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobDto convertToDto(Job job) {
        JobDto dto = new JobDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCompany(job.getCompany());
        dto.setLocation(job.getLocation());
        dto.setSalary(job.getSalary());
        return dto;
    }

}