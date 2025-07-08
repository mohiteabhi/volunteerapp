package com.example.volunteerapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class ForgotPasswordRequest {
    @Email @NotBlank
    @Getter @Setter
    private String email;
}
