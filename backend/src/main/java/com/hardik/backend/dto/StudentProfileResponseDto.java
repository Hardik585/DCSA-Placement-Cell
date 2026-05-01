package com.hardik.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StudentProfileResponseDto {

    private double cgpa;
    private String branch;
    private Integer graduationYear;
    private String resumeUrl;
    private String phone;
}
