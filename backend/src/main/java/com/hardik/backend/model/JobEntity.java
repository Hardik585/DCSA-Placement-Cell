package com.hardik.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double minCgpa;

    private Integer maxBacklogs;

    private String location;

    private Double packageAmount;

    private LocalDateTime deadline;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "eligible_course")
    private String eligibleCourse;

    //Company
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;


}