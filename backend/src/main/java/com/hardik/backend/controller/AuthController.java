package com.hardik.backend.controller;

import com.hardik.backend.dto.LoginRequest;
import com.hardik.backend.dto.UserRequestDto;
import com.hardik.backend.enums.Role;
import com.hardik.backend.service.UserService;
import com.hardik.backend.service.impl.AppUserDetailsService;
import com.hardik.backend.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Authentication authenticate = authenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();

        //extract role from authentication
        String role = userDetails
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replace("ROLE_", "");

        String token = jwtUtil.generateToken(userDetails.getUsername(), role);
        return ResponseEntity.ok(Map.of(
                "token", token
        ));
    }

    private Authentication authenticate(String email, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        Boolean isRegister = userService.registerUser(userRequestDto, Role.STUDENT);
        if (isRegister) {
            return ResponseEntity.ok("Register Successfully");
        } else {
            return ResponseEntity.badRequest().body("Register Failed");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin-createuser")
    public ResponseEntity<String> registerByAdmin(@Valid @RequestBody UserRequestDto userRequestDto,
                                                  @RequestParam Role role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities());
        Boolean isRegister = userService.registerUser(userRequestDto, role);
        if (isRegister) {
            return ResponseEntity.ok("Register Successfully");
        } else {
            return ResponseEntity.badRequest().body("Register Failed");
        }
    }
}
