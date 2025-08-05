package com.example.volunteerapp.controller;

import com.example.volunteerapp.dto.VolunteerEventCreateDTO;
import com.example.volunteerapp.dto.VolunteerEventUpdateDTO;
import com.example.volunteerapp.dto.VolunteerEventWithUserDTO;
import com.example.volunteerapp.entity.VolunteerEvent;
import com.example.volunteerapp.service.VolunteerEventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@Tag(name = "VolunteerEvent", description = "CRUD operations for VolunteerEvent")
public class VolunteerEventController {

    private final VolunteerEventService service;

    @Autowired
    public VolunteerEventController(VolunteerEventService service) {
        this.service = service;
    }

    // ————————— Create a new event —————————
    @PostMapping
    @Operation(summary = "Create a new volunteer event")
    public ResponseEntity<VolunteerEvent> createEvent(
            @Valid @RequestBody VolunteerEventCreateDTO createDTO) {
        VolunteerEvent saved = service.createEvent(createDTO);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/join")
    @Operation(summary = "Increment noOfVolJoined by 1 for the given event ID")
    public ResponseEntity<VolunteerEvent> joinEvent(@PathVariable Long id) {
        try {
            VolunteerEvent updated = service.incrementVolunteers(id);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ————————— Get only active events —————————
    @GetMapping("/active")
    @Operation(summary = "Get all active volunteer events with user info")
    public ResponseEntity<List<VolunteerEventWithUserDTO>> getActiveEventsWithUserInfo() {
        List<VolunteerEventWithUserDTO> list = service.getActiveEventsWithUserInfo();
        return ResponseEntity.ok(list);
    }

    // ————————— Get all events —————————
    @GetMapping
    @Operation(summary = "Get all volunteer events with user info")
    public ResponseEntity<List<VolunteerEventWithUserDTO>> getAllEventsWithUserInfo() {
        List<VolunteerEventWithUserDTO> list = service.getAllEventsWithUserInfo();
        return ResponseEntity.ok(list);
    }

    // ————————— Get event by ID —————————
    @GetMapping("/{id}")
    @Operation(summary = "Get a volunteer event by ID with user info")
    public ResponseEntity<VolunteerEventWithUserDTO> getEventByIdWithUserInfo(@PathVariable Long id) {
        return service.getEventByIdWithUserInfo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all volunteer events created by a given user")
    public ResponseEntity<List<VolunteerEventWithUserDTO>> getEventsByUser(
            @PathVariable Long userId) {
        List<VolunteerEventWithUserDTO> events = service.getEventsByUserIdWithUserInfo(userId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/user/{userId}/active")
    @Operation(summary = "Get all active volunteer events created by a given user")
    public ResponseEntity<List<VolunteerEventWithUserDTO>> getActiveEventsByUser(
            @PathVariable Long userId) {
        List<VolunteerEventWithUserDTO> events = service.getActiveEventsByUserWithUserInfo(userId);
        return ResponseEntity.ok(events);
    }

    // New endpoint: GET /api/events/city/{cityName}
    // ———————————————————————————————
    @GetMapping("/city/{cityName}")
    @Operation(summary = "Get events by city with user info")
    public ResponseEntity<List<VolunteerEventWithUserDTO>> getEventsByCityWithUserInfo(
            @PathVariable String cityName) {
        List<VolunteerEventWithUserDTO> events = service.getEventsByCityNameWithUserInfo(cityName);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/city/{cityName}/active")
    @Operation(summary = "Get active events by city with user info")
    public ResponseEntity<List<VolunteerEventWithUserDTO>> getActiveEventsByCityWithUserInfo(
            @PathVariable String cityName) {
        List<VolunteerEventWithUserDTO> events = service.getActiveEventsByCityWithUserInfo(cityName);
        return ResponseEntity.ok(events);
    }

    // ————————— Update event by ID —————————
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing volunteer event")
    public ResponseEntity<VolunteerEvent> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody VolunteerEventUpdateDTO updateDTO) {
        try {
            VolunteerEvent updated = service.updateEvent(id, updateDTO);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate an event")
    public ResponseEntity<Void> deactivateEvent(@PathVariable Long id) {
        try {
            service.deactivateEvent(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Activate an event (if not expired)")
    public ResponseEntity<Void> activateEvent(@PathVariable Long id) {
        try {
            service.activateEvent(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ————————— Utility endpoints —————————
    @PutMapping("/update-expired")
    @Operation(summary = "Update expired events status")
    public ResponseEntity<String> updateExpiredEvents() {
        service.updateExpiredEvents();
        return ResponseEntity.ok("Expired events updated successfully");
    }

    @PutMapping("/bulk-deactivate-expired")
    @Operation(summary = "Bulk deactivate all expired events")
    public ResponseEntity<String> bulkDeactivateExpiredEvents() {
        int count = service.bulkDeactivateExpiredEvents();
        return ResponseEntity.ok("Deactivated " + count + " expired events");
    }

    // ————————— Delete event by ID —————————
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a volunteer event by ID")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        try {
            service.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
