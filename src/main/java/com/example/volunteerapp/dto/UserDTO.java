package com.example.volunteerapp.dto;
import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String fullName;
    private Integer age;
    private String email;
    private String address;
    private String contactNo;

    public UserDTO(Long userId, String fullName, Integer age,
                   String email, String address, String contactNo) {
        this.userId = userId;
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.address = address;
        this.contactNo = contactNo;
    }
}
