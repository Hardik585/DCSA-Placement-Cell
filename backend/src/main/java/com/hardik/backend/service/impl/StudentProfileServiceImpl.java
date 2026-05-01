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
    public void saveProfile(StudentProfileRequestDto stdRequest, UserEntity userEntity) {
        StudentProfileEntity profileEntity = StdProfileMapper.toStdEntity(stdRequest, userEntity);
        stdProfileRepository.save(profileEntity);
    }

    @Override
    public StudentProfileResponseDto getProfile(Long uId) {
        StudentProfileEntity stdProfile = stdProfileRepository.findByUserId(uId).orElseThrow(() -> new RuntimeException("Student Profile Not Found"));
        return StdProfileMapper.toStdResponseDto(stdProfile);
    }
}
