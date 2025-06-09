package com.example.volunteerapp.service;

import com.example.volunteerapp.dto.UserEventJoinResponse;

import java.util.List;
import java.util.Optional;

public interface UserEventJoinService {
    List<UserEventJoinResponse> getAllJoins();
    Optional<UserEventJoinResponse> getJoinById(Long id);
    List<UserEventJoinResponse> getJoinsByUserId(Long userId);
    List<UserEventJoinResponse> getJoinsByEventId(Long eventId);
    void deleteJoinsByEventId(Long eventId);
}
