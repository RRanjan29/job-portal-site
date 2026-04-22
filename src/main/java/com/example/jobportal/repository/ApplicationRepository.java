package com.example.jobportal.repository;

import com.example.jobportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUserId(Long userId);

    List<Application> findByJobId(Long jobId);

    Optional<Application> findByUserIdAndJobId(Long userId, Long jobId);

}