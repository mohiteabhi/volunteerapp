package com.example.volunteerapp.controller;

import com.example.volunteerapp.dto.UserEventJoinResponse;
import com.example.volunteerapp.service.UserEventJoinService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/joins")
@Tag(name = "UserEventJoin", description = "Operations on user‐event join records")
public class UserEventJoinController {

    private final UserEventJoinService service;

    @Autowired
    public UserEventJoinController(UserEventJoinService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all join records with user & event details")
    public ResponseEntity<List<UserEventJoinResponse>> getAll() {
        return ResponseEntity.ok(service.getAllJoins());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a join record by its ID")
    public ResponseEntity<UserEventJoinResponse> getById(@PathVariable Long id) {
        return service.getJoinById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all join records for a given user ID")
    public ResponseEntity<List<UserEventJoinResponse>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getJoinsByUserId(userId));
    }

    @GetMapping("/event/{eventId}")
    @Operation(summary = "Get all join records for a given event ID")
    public ResponseEntity<List<UserEventJoinResponse>> getByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(service.getJoinsByEventId(eventId));
    }

    @DeleteMapping("/event/{eventId}")
    @Operation(summary = "Delete all join records for a given event ID")
    public ResponseEntity<Void> deleteByEvent(@PathVariable Long eventId) {
        service.deleteJoinsByEventId(eventId);
        return ResponseEntity.noContent().build();
    }
}
