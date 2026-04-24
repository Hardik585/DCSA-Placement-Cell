package com.hardik.backend.controller;

import com.hardik.backend.dto.ApprovalRequest;
import com.hardik.backend.dto.ReviewRequest;
import com.hardik.backend.model.UserEntity;
import com.hardik.backend.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/")
@RequiredArgsConstructor
public class ApprovalController {

    private final ApplicationService applicationService;

    // Approve or Reject request of the company
    @PutMapping("/{id}/approvals")
    public ResponseEntity<String> reviewRequest(@PathVariable Long id, @Valid @RequestBody ApprovalRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity admin = (UserEntity) auth.getPrincipal();
        String response = applicationService.reviewRequest(
                id,
                admin.getId(),
                request.getApprove()
        );
        return ResponseEntity.ok(response);
    }
}
