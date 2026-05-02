package com.hardik.backend.service;

import com.hardik.backend.dto.CompanyRequestDto;
import com.hardik.backend.dto.CompanyResponseDto;
import com.hardik.backend.model.UserEntity;

public interface CompanyService {

    public CompanyResponseDto registerCompany(CompanyRequestDto req, UserEntity user);

    public CompanyResponseDto updateCompany(CompanyRequestDto req, Long cId);
}
