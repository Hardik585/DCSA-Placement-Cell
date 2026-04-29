package com.hardik.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordDto {

    @Email(message = "Invalid email")
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "otp is required")
    private String resetOtp;
    @NotBlank(message = "New Password is required")
    private String newPassword;
}
