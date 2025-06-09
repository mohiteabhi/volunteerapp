package com.example.volunteerapp.dto;

import com.example.volunteerapp.entity.VolunteerEvent;
import com.example.volunteerapp.entity.UserInfo;
import lombok.Getter;

import java.time.Instant;

public class UserEventJoinResponse {

    @Getter
    private Long id;

    @Getter
    private Instant joinTime;

    @Getter
    private UserInfo user;

    @Getter
    private VolunteerEvent event;

    public UserEventJoinResponse(Long id, Instant joinTime, UserInfo user, VolunteerEvent event) {
        this.id = id;
        this.joinTime = joinTime;
        this.user = user;
        this.event = event;
    }
}

