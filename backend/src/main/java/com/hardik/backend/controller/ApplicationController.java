package com.hardik.backend.controller;

import com.hardik.backend.dto.ApplyRequest;
import com.hardik.backend.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    //Apply to job
    @PostMapping("/apply")
    public ResponseEntity<String> applyToJob(@RequestBody ApplyRequest request) {
        String response = applicationService.applyToJob(
                request.getStudentId(),
                request.getJobId()
        );
        return ResponseEntity.ok(response);
    }
}