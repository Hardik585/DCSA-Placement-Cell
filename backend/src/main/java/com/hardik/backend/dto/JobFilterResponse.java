package com.hardik.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class JobFilterResponse {

    private Long id;
    private String title;
    private String description;
    private String location;
    private Double packageAmount;
    private Double minCgpa;
    private Integer maxBacklog;
    private String eligibleCourse;
    private LocalDateTime deadline;
    private String companyName;

}
