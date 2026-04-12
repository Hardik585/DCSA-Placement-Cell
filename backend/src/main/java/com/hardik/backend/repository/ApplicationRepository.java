package com.hardik.backend.repository;

import com.hardik.backend.model.ApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
    long countByStudentId(Long studentId);

    boolean existsByStudentIdAndJobId(Long studentId, Long jobId);
}