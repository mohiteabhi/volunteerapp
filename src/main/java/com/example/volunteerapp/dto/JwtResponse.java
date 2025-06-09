package com.example.volunteerapp.dto;

public class JwtResponse {
    private String token;
    private String tokenType = "Bearer";
    private String fullName;
    private String email;

    public JwtResponse(String token, String fullName, String email) {
        this.token = token;
        this.fullName = fullName;
        this.email = email;
    }

    // ——— Getters ———

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }


}
