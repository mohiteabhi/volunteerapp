package com.example.volunteerapp.service;

import com.example.volunteerapp.dto.SuggestedEventDTO;
import com.example.volunteerapp.entity.UserInfo;
import com.example.volunteerapp.entity.VolunteerEvent;
import com.example.volunteerapp.repository.UserInfoRepository;
import com.example.volunteerapp.repository.VolunteerEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private final UserInfoRepository userRepo;
    private final VolunteerEventRepository eventRepo;

    @Autowired
    public RecommendationService(UserInfoRepository userRepo, VolunteerEventRepository eventRepo) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    public List<SuggestedEventDTO> suggestEventsForUser(Long userId) {
        UserInfo user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Set<String> userSkills = user.getSkills();
//        String userCity = extractCity(user.getAddress());
        String userCity = user.getCityName();

        // get upcoming events
        List<VolunteerEvent> events = eventRepo.findByEventDateAfter(LocalDate.now());

        // score and filter
        List<SuggestedEventDTO> suggestions = new ArrayList<>();
        for (VolunteerEvent e : events) {
            Set<String> required = e.getRequiredSkills();
            long matchCount = required.stream()
                    .filter(userSkills::contains)
                    .count();
            if (matchCount == 0) continue;

            double cityBonus = e.getCityName().equalsIgnoreCase(userCity) ? 1.0 : 0.5;
            double score = (matchCount / (double) required.size()) + cityBonus;

            SuggestedEventDTO dto = mapToDto(e);
            dto.setScore(score);
            suggestions.add(dto);
        }

        // sort by score desc
        return suggestions.stream()
                .sorted(Comparator.comparingDouble(SuggestedEventDTO::getScore).reversed())
                .collect(Collectors.toList());
    }

//    private String extractCity(String address) {
//        return address != null && address.contains(",") ? address.split(",")[0].trim() : address;
//    }

    private SuggestedEventDTO mapToDto(VolunteerEvent e) {
        SuggestedEventDTO dto = new SuggestedEventDTO();
        dto.setEventId(e.getId());
        dto.setCityName(e.getCityName());
        dto.setEventDes(e.getEventDes());
        dto.setTotalVol(e.getTotalVol());
        dto.setNoOfVolJoined(e.getNoOfVolJoined());
        dto.setEventDate(e.getEventDate());
        dto.setEventTime(e.getEventTime());
        dto.setAddress(e.getAddress());
        dto.setOrganizerName(e.getOrganizerName());
        dto.setContact(e.getContact());
        dto.setRequiredSkills(e.getRequiredSkills());
        return dto;
    }
}
