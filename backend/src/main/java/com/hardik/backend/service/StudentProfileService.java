package com.hardik.backend.service;


import com.hardik.backend.dto.StudentProfileRequestDto;
import com.hardik.backend.dto.StudentProfileResponseDto;
import com.hardik.backend.model.UserEntity;

public interface StudentProfileService {

    public StudentProfileResponseDto saveProfile(StudentProfileRequestDto stdRequest, UserEntity userEntity);
    public StudentProfileResponseDto getProfile(Long uId);
    public StudentProfileResponseDto updateProfile(Long uId, StudentProfileRequestDto stdRequest);
}
