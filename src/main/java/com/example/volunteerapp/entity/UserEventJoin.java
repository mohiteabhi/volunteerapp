package com.example.volunteerapp.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_event_join")
public class UserEventJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // who joined
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // which event
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    // when they joined
    @Column(name = "join_time", nullable = false, updatable = false)
    private Instant joinTime;

    public UserEventJoin() {}

    public UserEventJoin(Long userId, Long eventId) {
        this.userId   = userId;
        this.eventId  = eventId;
        this.joinTime = Instant.now();
    }

    // ——— Getters & Setters ———

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Instant getJoinTime() {
        return joinTime;
    }
    public void setJoinTime(Instant joinTime) {
        this.joinTime = joinTime;
    }
}
