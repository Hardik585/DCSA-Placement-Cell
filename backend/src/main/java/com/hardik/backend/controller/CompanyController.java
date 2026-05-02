package com.hardik.backend.controller;


import com.hardik.backend.dto.CompanyRequestDto;
import com.hardik.backend.dto.CompanyResponseDto;
import com.hardik.backend.model.UserEntity;
import com.hardik.backend.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    private UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCompany(@RequestBody CompanyRequestDto request) {
        UserEntity currentUser = getCurrentUser();
        CompanyResponseDto response = companyService.registerCompany(request, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update/{cId}")
    public ResponseEntity<?> updateCompany(@Valid @RequestBody CompanyRequestDto req, @PathVariable Long cId) {
        CompanyResponseDto response = companyService.updateCompany(req, cId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
