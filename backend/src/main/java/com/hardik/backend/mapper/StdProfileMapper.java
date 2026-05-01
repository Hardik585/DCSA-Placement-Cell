package com.hardik.backend.mapper;

import com.hardik.backend.dto.StudentProfileRequestDto;
import com.hardik.backend.dto.StudentProfileResponseDto;
import com.hardik.backend.model.StudentProfileEntity;
import com.hardik.backend.model.UserEntity;

public class StdProfileMapper {

    public static StudentProfileEntity toStdEntity(StudentProfileRequestDto stdRequest, UserEntity userEntity) {
        return StudentProfileEntity.builder()
                .cgpa(stdRequest.getCgpa())
                .phone(stdRequest.getPhone())
                .graduationYear(stdRequest.getGraduationYear())
                .branch(stdRequest.getBranch())
                .resumeUrl(stdRequest.getResumeUrl())
                .user(userEntity)
                .build();
    }

    public static StudentProfileResponseDto toStdResponseDto(StudentProfileEntity sp) {
        return StudentProfileResponseDto.builder()
                .cgpa(sp.getCgpa())
                .branch(sp.getBranch())
                .graduationYear(sp.getGraduationYear())
                .phone(sp.getPhone())
                .resumeUrl(sp.getResumeUrl())
                .build();
    }
}
