package com.hardik.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class JobFilterRequest {

    private String course;
    private String keyword;
    private Double minCgpa;
//    private LocalDateTime deadline;
}
