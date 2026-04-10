package com.hardik.backend.model;

import com.hardik.backend.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "applications",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "job_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    @CreationTimestamp
    private LocalDateTime appliedAt;

    // 🔗 Student (User)
    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserEntity student;

    // 🔗 Job
    @ManyToOne
    @JoinColumn(name = "job_id")
    private JobEntity job;
}
