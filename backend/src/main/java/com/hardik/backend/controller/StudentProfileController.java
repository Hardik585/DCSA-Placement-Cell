package com.hardik.backend.controller;

import com.hardik.backend.dto.StudentProfileRequestDto;
import com.hardik.backend.dto.StudentProfileResponseDto;
import com.hardik.backend.model.UserEntity;
import com.hardik.backend.service.StudentProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/std/profile")
@RequiredArgsConstructor
public class StudentProfileController {

    private final StudentProfileService stdService;

    private UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/api/save")
    public ResponseEntity<?> saveProfile(@Valid @RequestBody StudentProfileRequestDto stdRequest) {
        StudentProfileResponseDto responseDto = stdService.saveProfile(stdRequest, getCurrentUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/api/my")
    public ResponseEntity<?> getProfile() {
        UserEntity currentUser = getCurrentUser();
        StudentProfileResponseDto response = stdService.getProfile(currentUser.getId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/update")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody StudentProfileRequestDto stdRequest) {
        UserEntity currentUser = getCurrentUser();
        StudentProfileResponseDto response = stdService.updateProfile(currentUser.getId(), stdRequest);
        return ResponseEntity.ok(response);
    }
}
