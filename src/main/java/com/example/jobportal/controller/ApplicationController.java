package com.example.jobportal.controller;

import com.example.jobportal.dto.ApplicationDto;
import com.example.jobportal.entity.ApplicationStatus;
import com.example.jobportal.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<ApplicationDto> apply(@RequestParam("jobId") Long jobId,
                                                @RequestParam("resume") MultipartFile resume,
                                                @RequestAttribute("userId") Long userId) throws IOException {
        String fileName = userId + "_" + jobId + "_" + resume.getOriginalFilename();
        Path path = Paths.get("uploads/" + fileName);
        Files.createDirectories(path.getParent());
        Files.write(path, resume.getBytes());
        String resumePath = path.toString();
        ApplicationDto application = applicationService.applyForJob(userId, jobId, resumePath);
        return ResponseEntity.ok(application);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationDto>> getMyApplications(@RequestAttribute("userId") Long userId) {
        List<ApplicationDto> applications = applicationService.getApplicationsByUser(userId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationDto>> getApplicationsForJob(@PathVariable Long jobId,
                                                                       @RequestAttribute("role") String role) {
        if (!"RECRUITER".equals(role)) {
            return ResponseEntity.status(403).build();
        }
        List<ApplicationDto> applications = applicationService.getApplicationsByJob(jobId);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestParam("status") ApplicationStatus status,
                                             @RequestAttribute("role") String role) {
        if (!"RECRUITER".equals(role)) {
            return ResponseEntity.status(403).build();
        }
        applicationService.updateApplicationStatus(id, status);
        return ResponseEntity.noContent().build();
    }

}