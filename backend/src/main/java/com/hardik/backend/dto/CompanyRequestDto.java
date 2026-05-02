package com.hardik.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CompanyRequestDto {

    @NotBlank(message = "name is required")
    private String name;
    @Email(message = "invalid email")
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "name is required")
    private String description;
    @NotBlank(message = "name is required")
    private String website;

}
