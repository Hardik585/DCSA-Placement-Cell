package com.hardik.backend.controller;

import com.hardik.backend.dto.JobFilterRequest;
import com.hardik.backend.dto.JobResponse;
import com.hardik.backend.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @PostMapping("/search")
    public ResponseEntity<?> filterJobs(@Valid @RequestBody JobFilterRequest req,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        @RequestParam(defaultValue = "id") String sortBy,
                                        @RequestParam(defaultValue = "asc") String sortDir
    ) {

        System.out.println(req.toString());

        List<String> allowedSortFields = List.of("id", "packageAmount", "minCgpa", "deadline");

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "id";
        }
        size = Math.min(size, 50);
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(jobService.filterJobs(req, pageable));
    }

}