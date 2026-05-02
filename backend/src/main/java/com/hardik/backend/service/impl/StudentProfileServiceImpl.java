package com.hardik.backend.service.impl;

import com.hardik.backend.dto.StudentProfileRequestDto;
import com.hardik.backend.dto.StudentProfileResponseDto;
import com.hardik.backend.mapper.StdProfileMapper;
import com.hardik.backend.model.StudentProfileEntity;
import com.hardik.backend.model.UserEntity;
import com.hardik.backend.repository.StudentProfileRepository;
import com.hardik.backend.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl implements StudentProfileService {

    private final StudentProfileRepository stdProfileRepository;

    @Override
    public StudentProfileResponseDto saveProfile(StudentProfileRequestDto stdRequest, UserEntity userEntity) {
        if (stdProfileRepository.existsByUserId(userEntity.getId())) {
            throw new RuntimeException("Profile already exists. Use update instead.");
        }
        StudentProfileEntity profileEntity = StdProfileMapper.toStdEntity(stdRequest, userEntity);
        StudentProfileEntity savedProfile = stdProfileRepository.save(profileEntity);
        return StdProfileMapper.toStdResponseDto(savedProfile);
    }

    @Override
    public StudentProfileResponseDto getProfile(Long uId) {
        StudentProfileEntity stdProfile = stdProfileRepository.findByUserId(uId).orElseThrow(() -> new RuntimeException("Student Profile Not Found"));
        return StdProfileMapper.toStdResponseDto(stdProfile);
    }

    @Override
    public StudentProfileResponseDto updateProfile(Long uId, StudentProfileRequestDto stdRequest) {
        StudentProfileEntity stdProfile = stdProfileRepository.findByUserId(uId).orElseThrow(() -> new RuntimeException("Student Profile Not Found"));
        stdProfile.setBranch(stdRequest.getBranch());
        stdProfile.setCgpa(stdRequest.getCgpa());
        stdProfile.setPhone(stdRequest.getPhone());
        stdProfile.setResumeUrl(stdRequest.getResumeUrl());
        stdProfile.setGraduationYear(stdRequest.getGraduationYear());
        StudentProfileEntity savedProfile = stdProfileRepository.save(stdProfile);
        return StdProfileMapper.toStdResponseDto(savedProfile);
    }
}
