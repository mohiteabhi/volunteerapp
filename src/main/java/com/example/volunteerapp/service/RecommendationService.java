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

        List<SuggestedEventDTO> skillMatched = new ArrayList<>();
        List<SuggestedEventDTO> cityFallback = new ArrayList<>();
        for (VolunteerEvent e : events) {
            Set<String> required = e.getRequiredSkills();
            long matchCount = required.stream()
                    .filter(userSkills::contains)
                    .count();

            double cityBonus = e.getCityName().equalsIgnoreCase(userCity) ? 1.0 : 0.0;

            if (matchCount > 0) {
                // skill + city scoring
                double score = (matchCount / (double) required.size()) + cityBonus;
                SuggestedEventDTO dto = toDto(e, score);
                skillMatched.add(dto);
            } else if (cityBonus > 0) {
                // no skill match, but same city fallback
                double score = cityBonus; // e.g., 1.0
                SuggestedEventDTO dto = toDto(e, score);
                cityFallback.add(dto);
            }
            // else skip event
        }
        skillMatched.sort(Comparator.comparingDouble(SuggestedEventDTO::getScore).reversed());
        cityFallback.sort(Comparator.comparingDouble(SuggestedEventDTO::getScore).reversed());

        // combine: skills first, then city-only
        List<SuggestedEventDTO> combined = new ArrayList<>();
        combined.addAll(skillMatched);
        combined.addAll(cityFallback);
        return combined;


        // score and filter
//        List<SuggestedEventDTO> suggestions = new ArrayList<>();
//        for (VolunteerEvent e : events) {
//            Set<String> required = e.getRequiredSkills();
//            long matchCount = required.stream()
//                    .filter(userSkills::contains)
//                    .count();
//            if (matchCount == 0) continue;
//
//            double cityBonus = e.getCityName().equalsIgnoreCase(userCity) ? 1.0 : 0.5;
//            double score = (matchCount / (double) required.size()) + cityBonus;
//
//            SuggestedEventDTO dto = mapToDto(e);
//            dto.setScore(score);
//            suggestions.add(dto);
//        }
//
//        // sort by score desc
//        return suggestions.stream()
//                .sorted(Comparator.comparingDouble(SuggestedEventDTO::getScore).reversed())
//                .collect(Collectors.toList());
    }

//    private String extractCity(String address) {
//        return address != null && address.contains(",") ? address.split(",")[0].trim() : address;
//    }

//    private SuggestedEventDTO mapToDto(VolunteerEvent e) {
//        SuggestedEventDTO dto = new SuggestedEventDTO();
//        dto.setEventId(e.getId());
//        dto.setCityName(e.getCityName());
//        dto.setEventDes(e.getEventDes());
//        dto.setTotalVol(e.getTotalVol());
//        dto.setNoOfVolJoined(e.getNoOfVolJoined());
//        dto.setEventDate(e.getEventDate());
//        dto.setEventTime(e.getEventTime());
//        dto.setAddress(e.getAddress());
//        dto.setOrganizerName(e.getOrganizerName());
//        dto.setContact(e.getContact());
//        dto.setRequiredSkills(e.getRequiredSkills());
//        return dto;
//    }
    private SuggestedEventDTO toDto(VolunteerEvent e, double score) {
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
        dto.setScore(score);
        return dto;
    }
}
