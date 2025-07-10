package com.example.volunteerapp.controller;

import com.example.volunteerapp.dto.UserDTO;
import com.example.volunteerapp.entity.UserInfo;
import com.example.volunteerapp.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserInfo> users = userInfoService.getAllUsers();
        List<UserDTO> dtos = users.stream()
                .map(user -> new UserDTO(
                        user.getUserId(),
                        user.getFullName(),
                        user.getAge(),
                        user.getEmail(),
                        user.getAddress(),
                        user.getContactNo(),
                        user.getSkills()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserInfo user = userInfoService.getUserById(userId);
        UserDTO dto = new UserDTO(
                user.getUserId(),
                user.getFullName(),
                user.getAge(),
                user.getEmail(),
                user.getAddress(),
                user.getContactNo(),
                user.getSkills()
        );
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserInfo> updateUser(
            @PathVariable Long userId,
            @RequestBody UserInfo userInfo) {
        UserInfo updated = userInfoService.updateUser(userId, userInfo);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userInfoService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
