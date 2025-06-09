package com.example.volunteerapp.controller;

import com.example.volunteerapp.dto.VolunteerEventCreateDTO;
import com.example.volunteerapp.dto.VolunteerEventUpdateDTO;
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

    // ————————— Get all events —————————
    @GetMapping
    @Operation(summary = "Get all volunteer events")
    public ResponseEntity<List<VolunteerEvent>> getAllEvents() {
        List<VolunteerEvent> list = service.getAllEvents();
        return ResponseEntity.ok(list);
    }

    // ————————— Get event by ID —————————
    @GetMapping("/{id}")
    @Operation(summary = "Get a volunteer event by ID")
    public ResponseEntity<VolunteerEvent> getEventById(@PathVariable Long id) {
        return service.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // New endpoint: GET /api/events/city/{cityName}
    // ———————————————————————————————
    @GetMapping("/city/{cityName}")
    @Operation(summary = "Get all volunteer events by city name")
    public ResponseEntity<List<VolunteerEvent>> getEventsByCity(
            @PathVariable String cityName) {
        List<VolunteerEvent> events = service.getEventsByCityName(cityName);
        if (events.isEmpty()) {
            // Optionally: return 404 or return empty list with 200.
            // Here we return 200 and an empty list if no events found for that city.
            return ResponseEntity.ok(events);
        }
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
