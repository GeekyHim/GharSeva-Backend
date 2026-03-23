package com.example.gharseva.Dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequestDto {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;
}