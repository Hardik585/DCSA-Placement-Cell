package com.hardik.backend.dto;


import com.hardik.backend.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "username is required")
    private String username;
    @Email(message = "Invalid email")
    @NotBlank(message = "email is required")
    private String email;

}
