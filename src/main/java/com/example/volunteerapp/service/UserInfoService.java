package com.example.volunteerapp.service;

import com.example.volunteerapp.entity.UserInfo;
import java.util.List;

public interface UserInfoService {
    List<UserInfo> getAllUsers();
    UserInfo getUserById(Long userId);
    UserInfo updateUser(Long userId, UserInfo userInfo);
    void deleteUser(Long userId);
}
