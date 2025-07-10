package com.example.volunteerapp.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
public class UserDTO {
    @Getter @Setter
    private Long userId;
    @Getter @Setter
    private String fullName;
    @Getter @Setter
    private Integer age;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String address;
    @Getter @Setter
    private String contactNo;
    @Getter @Setter
    @NotEmpty(message = "At least one skill must be provided")
    private Set<@NotBlank String> skills;

    public UserDTO(Long userId, String fullName, Integer age,
                   String email, String address, String contactNo, Set<String> skills) {
        this.userId = userId;
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.address = address;
        this.contactNo = contactNo;
        this.skills = skills;
    }
}
