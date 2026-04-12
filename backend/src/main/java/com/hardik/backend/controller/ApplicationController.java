package com.hardik.backend.controller;

import com.hardik.backend.dto.ApplyJobRequest;
import com.hardik.backend.model.UserEntity;
import com.hardik.backend.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    //Apply to job
    @PostMapping("/apply")
    public ResponseEntity<String> applyToJob(@Valid @RequestBody ApplyJobRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity student = (UserEntity) auth.getPrincipal();
        String response = applicationService.applyToJob(
                student.getId(),
                request.getJobId()
        );
        return ResponseEntity.ok(response);
    }
}