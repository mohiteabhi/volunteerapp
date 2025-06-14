package com.example.volunteerapp.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
