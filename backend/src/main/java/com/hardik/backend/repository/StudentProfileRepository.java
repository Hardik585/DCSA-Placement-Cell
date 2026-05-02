package com.hardik.backend.repository;

import com.hardik.backend.model.StudentProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentProfileRepository extends JpaRepository<StudentProfileEntity, Long> {
    Optional<StudentProfileEntity> findByUserId(Long userId);
     Boolean existsByUserId(Long userId);

}
