package com.hardik.backend.mapper;

import com.hardik.backend.dto.CompanyRequestDto;
import com.hardik.backend.dto.CompanyResponseDto;
import com.hardik.backend.model.CompanyEntity;
import com.hardik.backend.model.UserEntity;

public class CompanyMapper {

    public static CompanyEntity toEntity(CompanyRequestDto requestDto, UserEntity user) {
        return CompanyEntity.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .description(requestDto.getDescription())
                .website(requestDto.getWebsite())
                .createdBy(user)
                .build();
    }

    public static CompanyResponseDto toDto(CompanyEntity companyEntity) {
        return CompanyResponseDto.builder()
                .cId(companyEntity.getId())
                .name(companyEntity.getName())
                .email(companyEntity.getEmail())
                .description(companyEntity.getDescription())
                .website(companyEntity.getWebsite())
                .build();
    }
}
