package com.hardik.backend.controller;

import com.hardik.backend.dto.LoginRequest;
import com.hardik.backend.dto.ChangePasswordDto;
import com.hardik.backend.dto.ResetPasswordDto;
import com.hardik.backend.dto.UserRequestDto;
import com.hardik.backend.enums.Role;
import com.hardik.backend.service.UserService;
import com.hardik.backend.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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


    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto request) {
        userService.changePassword(request.getEmail(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok("Reset Successfully");
    }

    @PostMapping("/send-reset-otp")
    public ResponseEntity<?> sendResetOtpRequest(@RequestBody String email) {
        userService.sendResetOtp(email);
        return ResponseEntity.ok("Reset Otp send successfully to Email: " + email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordDto request) {
        userService.verifyResetOtpAndChangePassword(request.getEmail(), request.getResetOtp(), request.getNewPassword());
        return ResponseEntity.ok("Reset Password Successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) //make sure to make it true in the production
                .path("/")
                .maxAge(0)
                .sameSite("Lax")  // safer for frontend-backend setups
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged out Successfully");
    }
}
