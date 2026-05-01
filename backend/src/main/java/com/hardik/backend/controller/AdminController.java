package com.hardik.backend.controller;


import com.hardik.backend.dto.UserRequestDto;
import com.hardik.backend.enums.Role;
import com.hardik.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-user")
    public ResponseEntity<String> registerByAdmin(@Valid @RequestBody UserRequestDto userRequestDto, @RequestParam Role role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Boolean isRegister = userService.registerUser(userRequestDto, role);
        if (isRegister) {
            return ResponseEntity.ok("Register Successfully");
        } else {
            return ResponseEntity.badRequest().body("Register Failed");
        }
    }

}
