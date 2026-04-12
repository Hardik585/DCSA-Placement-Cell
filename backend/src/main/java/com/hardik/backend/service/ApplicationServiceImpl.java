package com.hardik.backend.service;

import com.hardik.backend.enums.ApprovalStatus;
import com.hardik.backend.enums.Role;
import com.hardik.backend.model.*;
import com.hardik.backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final ApprovalRequestRepository approvalRequestRepository;

    private static final int MAX_APPLICATION_LIMIT = 5; // later I make dynamic

    @Override
    @Transactional
    public String applyToJob(Long studentId, Long jobId) {
        UserEntity student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        JobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        StudentProfileEntity profile = studentProfileRepository.findByUserId(studentId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        // checking for duplicate and blocking it
        if (applicationRepository.existsByStudentIdAndJobId(studentId, jobId)) {
            throw new RuntimeException("Already applied to this job");
        }
        if ( profile.getCgpa() < job.getMinCgpa()) {
            throw new RuntimeException("Not eligible for this job");
        }
//        check application limit
        long count = applicationRepository.countByStudentId(studentId);
        if (count < MAX_APPLICATION_LIMIT) {
            ApplicationEntity application = ApplicationEntity.builder()
                    .student(student)
                    .job(job)
                    .build();
            applicationRepository.save(application);
            return "Application submitted successfully";
        } else {
            // here if count is greater then the limit so sending req to admin for approval
            if (approvalRequestRepository.existsByStudentIdAndJobId(studentId, jobId)) {
                throw new RuntimeException("Approval request already exists");
            }
            ApplicationApprovalRequestEntity request = ApplicationApprovalRequestEntity.builder()
                    .student(student)
                    .job(job)
                    .build();
            approvalRequestRepository.save(request);
            return "Limit reached. Approval request sent to admin.";
        }
    }

    //Admin Approval Logic
    @Override
    @Transactional
    public String reviewRequest(Long requestId, Long adminId, boolean approve) {
        ApplicationApprovalRequestEntity request = approvalRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        UserEntity admin = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        //Role validation
        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new RuntimeException("Only admin can review requests");
        }

        //Checking whether job is available or not
        JobEntity job = request.getJob();
        if (job == null) {
            throw new RuntimeException("Job not found");
        }
        if (job.getDeadline().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Application deadline passed");
        }

        if (approve) {
            request.setStatus(ApprovalStatus.APPROVED);
            request.setReviewedBy(admin);
            request.setReviewedAt(LocalDateTime.now());
            //Create actual application
            ApplicationEntity application = ApplicationEntity.builder()
                    .student(request.getStudent())
                    .job(request.getJob())
                    .build();
            applicationRepository.save(application);
        } else {
            request.setStatus(ApprovalStatus.REJECTED);
            request.setReviewedBy(admin);
            request.setReviewedAt(LocalDateTime.now());
        }
        approvalRequestRepository.save(request);
        return approve ? "Application approved" : "Application rejected";
    }
}
