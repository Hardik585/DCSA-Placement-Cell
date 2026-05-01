package com.hardik.backend.controller;

import com.hardik.backend.dto.StudentProfileRequestDto;
import com.hardik.backend.dto.StudentProfileResponseDto;
import com.hardik.backend.model.UserEntity;
import com.hardik.backend.service.StudentProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
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
    @PostMapping("/save")
    public ResponseEntity<?> saveProfile(@Valid @RequestBody StudentProfileRequestDto stdRequest) {
        System.out.println("current user : " + getCurrentUser());
        stdService.saveProfile(stdRequest, getCurrentUser());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/my")
    public ResponseEntity<StudentProfileResponseDto> getProfile() {
        UserEntity currentUser = getCurrentUser();
        StudentProfileResponseDto response = stdService.getProfile(currentUser.getId());
        return ResponseEntity.ok(response);
    }
}
