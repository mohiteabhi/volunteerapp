package com.example.volunteerapp.service;

import com.example.volunteerapp.dto.UserDTO;
import com.example.volunteerapp.entity.UserInfo;
import com.example.volunteerapp.repository.UserInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }

    @Override
    public UserInfo getUserById(Long userId) {
        return userInfoRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public UserInfo updateUser(Long userId, UserInfo userInfo) {
        UserInfo existing = getUserById(userId);
        existing.setFullName(userInfo.getFullName());
        existing.setAge(userInfo.getAge());
        existing.setEmail(userInfo.getEmail());
        existing.setAddress(userInfo.getAddress());
        existing.setContactNo(userInfo.getContactNo());
        return userInfoRepository.save(existing);
    }

    @Override
    public void deleteUser(Long userId) {
        userInfoRepository.deleteById(userId);
    }
}
