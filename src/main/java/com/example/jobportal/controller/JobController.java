package com.example.jobportal.controller;

import com.example.jobportal.dto.JobCreateRequest;
import com.example.jobportal.dto.JobDto;
import com.example.jobportal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<Page<JobDto>> getAllJobs(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Page<JobDto> jobs = jobService.getAllJobs(page, size);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDto> getJobById(@PathVariable Long id) {
        JobDto job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @PostMapping
    public ResponseEntity<JobDto> createJob(@Valid @RequestBody JobCreateRequest request,
                                            @RequestAttribute("userId") Long userId,
                                            @RequestAttribute("role") String role) {
        if (!"RECRUITER".equals(role)) {
            return ResponseEntity.status(403).build();
        }
        JobDto job = jobService.createJob(request, userId);
        return ResponseEntity.ok(job);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDto> updateJob(@PathVariable Long id,
                                            @Valid @RequestBody JobCreateRequest request,
                                            @RequestAttribute("userId") Long userId,
                                            @RequestAttribute("role") String role) {
        if (!"RECRUITER".equals(role)) {
            return ResponseEntity.status(403).build();
        }
        JobDto job = jobService.updateJob(id, request, userId);
        return ResponseEntity.ok(job);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id,
                                          @RequestAttribute("userId") Long userId,
                                          @RequestAttribute("role") String role) {
        if (!"RECRUITER".equals(role)) {
            return ResponseEntity.status(403).build();
        }
        jobService.deleteJob(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my")
    public ResponseEntity<List<JobDto>> getMyJobs(@RequestAttribute("userId") Long userId,
                                                  @RequestAttribute("role") String role) {
        if (!"RECRUITER".equals(role)) {
            return ResponseEntity.status(403).build();
        }
        List<JobDto> jobs = jobService.getJobsByRecruiter(userId);
        return ResponseEntity.ok(jobs);
    }

}