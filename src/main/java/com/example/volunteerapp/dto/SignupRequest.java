package com.example.volunteerapp.dto;

import jakarta.validation.constraints.*;

public class SignupRequest {
    @NotBlank @Size(min = 2, max = 100)
    private String fullName;

    @NotNull @Min(1) @Max(150)
    private Integer age;

    @Email
    private String email;

    private String address;

    @NotBlank @Size(min = 10, max = 15)
    private String contactNo;

    @NotBlank @Size(min = 6, max = 100)
    private String password;

    // ——— Getters & Setters ———

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
