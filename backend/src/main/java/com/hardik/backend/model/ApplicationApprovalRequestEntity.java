package com.hardik.backend.model;

import com.hardik.backend.enums.ApplicationStatus;
import com.hardik.backend.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "application_approval_requests",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "job_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationApprovalRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime requestedAt;

    private LocalDateTime reviewedAt;

    // Student
    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserEntity student;

    // Job
    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobEntity job;

    // Admin who reviewed
    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private UserEntity reviewedBy;
}