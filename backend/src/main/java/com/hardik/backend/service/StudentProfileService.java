package com.hardik.backend.service;


import com.hardik.backend.dto.StudentProfileRequestDto;
import com.hardik.backend.dto.StudentProfileResponseDto;
import com.hardik.backend.model.UserEntity;

public interface StudentProfileService {

    public void saveProfile(StudentProfileRequestDto stdRequest, UserEntity userEntity);
    public StudentProfileResponseDto getProfile(Long uId);
}
