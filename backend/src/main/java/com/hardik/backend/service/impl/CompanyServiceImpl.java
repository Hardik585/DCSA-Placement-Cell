package com.hardik.backend.service.impl;

import com.hardik.backend.dto.CompanyRequestDto;
import com.hardik.backend.dto.CompanyResponseDto;
import com.hardik.backend.mapper.CompanyMapper;
import com.hardik.backend.model.CompanyEntity;
import com.hardik.backend.model.UserEntity;
import com.hardik.backend.repository.CompanyRepository;
import com.hardik.backend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponseDto registerCompany(CompanyRequestDto req, UserEntity user) {
        if (companyRepository.existsByName(req.getName())) {
            throw new RuntimeException("Company with name " + req.getName() + " already exists");
        }
        CompanyEntity company = CompanyMapper.toEntity(req, user);
        CompanyEntity isSaved = companyRepository.save(company);
        return CompanyMapper.toDto(isSaved);
    }

    @Override
    public CompanyResponseDto updateCompany(CompanyRequestDto req, Long cId) {
        CompanyEntity companyEntity = companyRepository.findById(cId).orElseThrow(() -> new RuntimeException("Company with email " + req.getEmail() + " not found"));
        companyEntity.setName(req.getName());
        companyEntity.setEmail(req.getEmail());
        companyEntity.setDescription(req.getDescription());
        companyEntity.setWebsite(req.getWebsite());
        CompanyEntity updatedEntity = companyRepository.save(companyEntity);
        return CompanyMapper.toDto(updatedEntity);
    }
}
