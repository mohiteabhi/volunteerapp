package com.example.volunteerapp.dto;

public class JwtResponse {
    private String token;
    private String tokenType = "Bearer";
    private String fullName;
    private String email;
//    private Long userId;

    public JwtResponse(String token, String fullName, String email) {
        this.token = token;
        this.fullName = fullName;
        this.email = email;
//        this.userId = userId;
    }

    // ——— Getters ———

//    public Long getUserId() {
//        return userId;
//    }

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
