package com.example.volunteerapp.repository;

import com.example.volunteerapp.entity.UserEventJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserEventJoinRepository extends JpaRepository<UserEventJoin, Long> {
    List<UserEventJoin> findByUserId(Long userId);
    List<UserEventJoin> findByEventId(Long eventId);
    void deleteByEventId(Long eventId);
}
