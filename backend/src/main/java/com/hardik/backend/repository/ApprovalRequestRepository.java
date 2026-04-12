package com.hardik.backend.repository;

import com.hardik.backend.model.ApplicationApprovalRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRequestRepository extends JpaRepository<ApplicationApprovalRequestEntity, Long> {
    boolean existsByStudentIdAndJobId(Long studentId, Long jobId);
}