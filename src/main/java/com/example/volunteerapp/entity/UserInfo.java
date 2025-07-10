package com.example.volunteerapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(min = 2, max = 100)
    private String fullName;

    private Integer age;

    @Email
    @Column(unique = true)
    private String email;

    private String address;

    @NotBlank
    @Size(min = 10, max = 15)
    private String contactNo;

    @NotBlank
    private String password; // will store the BCrypt hash

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_skills",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "skill")
    private Set<String> skills;

    // ——— Getters & Setters ———

    public Long getUserId() {
        return userId;
    }

    // No setter for userId if you never want to change the generated value,
    // but it’s harmless to include one:
    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Set<String> getSkills() {
        return skills;
    }
    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }
}
