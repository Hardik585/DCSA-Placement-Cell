package com.hardik.backend.controller;

import com.hardik.backend.dto.ApplyRequest;
import com.hardik.backend.model.UserEntity;
import com.hardik.backend.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    //Apply to job
    @PostMapping("/apply")
    public ResponseEntity<String> applyToJob(@RequestBody ApplyRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        String response = applicationService.applyToJob(
                request.getStudentId(),
                request.getJobId()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}