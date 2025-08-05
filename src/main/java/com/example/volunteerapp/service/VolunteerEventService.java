package com.example.volunteerapp.service;

import com.example.volunteerapp.dto.VolunteerEventCreateDTO;
import com.example.volunteerapp.dto.VolunteerEventUpdateDTO;
import com.example.volunteerapp.dto.VolunteerEventWithUserDTO;
import com.example.volunteerapp.entity.VolunteerEvent;

import java.util.List;
import java.util.Optional;

public interface VolunteerEventService {

    VolunteerEvent createEvent(VolunteerEventCreateDTO createDTO);

    VolunteerEvent incrementVolunteers(Long id);

    Optional<VolunteerEvent> getEventById(Long id);

    List<VolunteerEvent> getAllEvents();

    List<VolunteerEvent> getEventsByCityName(String cityName);

    VolunteerEvent updateEvent(Long id, VolunteerEventUpdateDTO updateDTO);

    void deleteEvent(Long id);

    List<VolunteerEventWithUserDTO> getAllEventsWithUserInfo();
    Optional<VolunteerEventWithUserDTO> getEventByIdWithUserInfo(Long id);
    List<VolunteerEventWithUserDTO> getEventsByCityNameWithUserInfo(String cityName);

    List<VolunteerEventWithUserDTO> getEventsByUserIdWithUserInfo(Long userId);

    // New methods for isActive functionality
    List<VolunteerEventWithUserDTO> getActiveEventsWithUserInfo();
    List<VolunteerEventWithUserDTO> getActiveEventsByCityWithUserInfo(String cityName);
    List<VolunteerEventWithUserDTO> getActiveEventsByUserWithUserInfo(Long userId);

    void deactivateEvent(Long id);
    void activateEvent(Long id);
    void updateExpiredEvents();
    int bulkDeactivateExpiredEvents();
}
