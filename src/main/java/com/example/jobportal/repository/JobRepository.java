package com.example.jobportal.repository;

import com.example.jobportal.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findAll(Pageable pageable);

    List<Job> findByRecruiterId(Long recruiterId);

}