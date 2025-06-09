package com.example.volunteerapp.service;

import com.example.volunteerapp.dto.UserEventJoinResponse;
import com.example.volunteerapp.entity.UserEventJoin;
import com.example.volunteerapp.entity.UserInfo;
import com.example.volunteerapp.entity.VolunteerEvent;
import com.example.volunteerapp.repository.UserEventJoinRepository;
import com.example.volunteerapp.repository.UserInfoRepository;
import com.example.volunteerapp.repository.VolunteerEventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserEventJoinServiceImpl implements UserEventJoinService{
    private final UserEventJoinRepository joinRepo;
    private final UserInfoRepository    userRepo;
    private final VolunteerEventRepository eventRepo;

    @Autowired
    public UserEventJoinServiceImpl(
            UserEventJoinRepository joinRepo,
            UserInfoRepository userRepo,
            VolunteerEventRepository eventRepo
    ) {
        this.joinRepo = joinRepo;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    @Override
    public List<UserEventJoinResponse> getAllJoins() {
        return joinRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserEventJoinResponse> getJoinById(Long id) {
        return joinRepo.findById(id)
                .map(this::toResponse);
    }

    @Override
    public List<UserEventJoinResponse> getJoinsByUserId(Long userId) {
        return joinRepo.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserEventJoinResponse> getJoinsByEventId(Long eventId) {
        return joinRepo.findByEventId(eventId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteJoinsByEventId(Long eventId) {
        joinRepo.deleteByEventId(eventId);
    }

    private UserEventJoinResponse toResponse(UserEventJoin join) {
        Long userId = join.getUserId();
        Long eventId = join.getEventId();

        UserInfo user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        VolunteerEvent event = eventRepo.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found: " + eventId));

        return new UserEventJoinResponse(
                join.getId(),
                join.getJoinTime(),
                user,
                event
        );
    }


}
