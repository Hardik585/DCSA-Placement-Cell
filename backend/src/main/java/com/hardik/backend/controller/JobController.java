package com.hardik.backend.controller;

import com.hardik.backend.dto.JobResponse;
import com.hardik.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<?> fetchAllJobs(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") Integer size) {
        JobResponse<?> jobResponse = jobService.fetchNextJobs(cursor, size);
        return ResponseEntity.ok(jobResponse);
    }

}