package com.example.volunteerapp.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class ResetPasswordRequest {
    @NotBlank
    @Getter @Setter
    private String token;

    @NotBlank
    @Getter @Setter
    private String newPassword;
}
