package com.hardik.backend.controller;

import com.hardik.backend.dto.ReviewRequest;
import com.hardik.backend.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/approvals")
@RequiredArgsConstructor
public class ApprovalController {

    private final ApplicationService applicationService;

    // Approve or Reject request of the company
    @PutMapping("/{requestId}/review")
    public ResponseEntity<String> reviewRequest(
            @PathVariable Long requestId,
            @RequestBody ReviewRequest request
    ) {
        String response = applicationService.reviewRequest(
                requestId,
                request.getAdminId(),
                request.isApprove()
        );

        return ResponseEntity.ok(response);
    }
}
