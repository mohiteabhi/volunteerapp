package com.example.volunteerapp.service;

import com.example.volunteerapp.dto.VolunteerEventCreateDTO;
import com.example.volunteerapp.dto.VolunteerEventUpdateDTO;
import com.example.volunteerapp.dto.VolunteerEventWithUserDTO;
import com.example.volunteerapp.entity.UserInfo;
import com.example.volunteerapp.entity.VolunteerEvent;
import com.example.volunteerapp.entity.UserEventJoin;
import com.example.volunteerapp.repository.UserInfoRepository;
import com.example.volunteerapp.repository.VolunteerEventRepository;
import com.example.volunteerapp.repository.UserEventJoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolunteerEventServiceImpl implements VolunteerEventService {

    private final VolunteerEventRepository repository;
    private final UserEventJoinRepository joinRepo;
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public VolunteerEventServiceImpl(VolunteerEventRepository repository, UserEventJoinRepository joinRepo, UserInfoRepository userInfoRepository) {
        this.repository = repository;
        this.joinRepo  = joinRepo;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public VolunteerEvent createEvent(VolunteerEventCreateDTO dto) {
        // Map DTO → Entity
        VolunteerEvent event = new VolunteerEvent();
        event.setUserId(dto.getUserId());
        event.setCityName(dto.getCityName());
        event.setEventDes(dto.getEventDes());
        event.setTotalVol(dto.getTotalVol());
        event.setNoOfVolJoined(dto.getNoOfVolJoined());
        event.setEventDate(dto.getEventDate());
        event.setEventTime(dto.getEventTime());
        event.setAddress(dto.getAddress());
        event.setOrganizerName(dto.getOrganizerName());
        event.setContact(dto.getContact());
        event.setRequiredSkills(dto.getRequiredSkills());

        return repository.save(event);
    }

    @Override
    public VolunteerEvent incrementVolunteers(Long id) {
        VolunteerEvent event = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + id));

        // If event not full, increment and save
        if (event.getNoOfVolJoined() < event.getTotalVol()) {
            event.setNoOfVolJoined(event.getNoOfVolJoined() + 1);
            // 1) Record the join
            Long userId = getCurrentUserId();
            UserEventJoin join = new UserEventJoin(userId, id);
            joinRepo.save(join);

            // 2) Save updated event
            return repository.save(event);
        }

        // Already full
        return event;
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // We assume your UserDetailsImpl stores the userId
        Object principal = auth.getPrincipal();
        if (principal instanceof com.example.volunteerapp.service.UserDetailsImpl) {
            return ((com.example.volunteerapp.service.UserDetailsImpl) principal).getId();
        }
        throw new EntityNotFoundException("Cannot extract user id from security context");
    }



    @Override
    public Optional<VolunteerEvent> getEventById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<VolunteerEvent> getAllEvents() {
        return repository.findAll();
    }

    @Override
    public List<VolunteerEvent> getEventsByCityName(String cityName) {
        return repository.findByCityName(cityName);
    }

    @Override
    public VolunteerEvent updateEvent(Long id, VolunteerEventUpdateDTO dto) {
        // Check if event exists
        VolunteerEvent existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event with id " + id + " not found"));

        // Map update fields
        existing.setCityName(dto.getCityName());
        existing.setEventDes(dto.getEventDes());
        existing.setTotalVol(dto.getTotalVol());
        existing.setNoOfVolJoined(dto.getNoOfVolJoined());
        existing.setEventDate(dto.getEventDate());
        existing.setEventTime(dto.getEventTime());
        existing.setAddress(dto.getAddress());
        existing.setOrganizerName(dto.getOrganizerName());
        existing.setContact(dto.getContact());

        existing.setRequiredSkills(dto.getRequiredSkills());

        return repository.save(existing);
    }

    @Override
    public void deleteEvent(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Event with id " + id + " not found");
        }
        repository.deleteById(id);
    }

    @Override
    public List<VolunteerEventWithUserDTO> getAllEventsWithUserInfo() {
        return repository.findAll().stream()
                .map(this::toEventWithUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VolunteerEventWithUserDTO> getEventByIdWithUserInfo(Long id) {
        return repository.findById(id)
                .map(this::toEventWithUserDTO);
    }

    @Override
    public List<VolunteerEventWithUserDTO> getEventsByCityNameWithUserInfo(String cityName) {
        return repository.findByCityName(cityName).stream()
                .map(this::toEventWithUserDTO)
                .collect(Collectors.toList());
    }

    public List<VolunteerEventWithUserDTO> getEventsByUserIdWithUserInfo(Long userId) {
        // fetch all events where event.userId == userId
        return repository.findByUserId(userId).stream()
                .map(this::toEventWithUserDTO)
                .collect(Collectors.toList());
    }

    private VolunteerEventWithUserDTO toEventWithUserDTO(VolunteerEvent event) {
        UserInfo user = userInfoRepository.findById(event.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with ID: " + event.getUserId()
                ));
        return new VolunteerEventWithUserDTO(event, user);
    }



}
