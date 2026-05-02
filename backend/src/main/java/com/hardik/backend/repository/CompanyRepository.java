package com.hardik.backend.repository;

import com.hardik.backend.model.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    boolean existsByName(String companyName);
    Optional<CompanyEntity> findById(Long cId);
}
