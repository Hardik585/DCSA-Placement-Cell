package com.hardik.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentProfileRequestDto {

    private double cgpa;
    private String branch;
    private Integer graduationYear;
    private String resumeUrl;
    private String phone;
}
