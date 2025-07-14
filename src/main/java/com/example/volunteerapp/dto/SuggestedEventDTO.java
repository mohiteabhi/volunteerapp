package com.example.volunteerapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class SuggestedEventDTO {
    @Getter @Setter
    private Long eventId;
    @Getter @Setter
    private String cityName;
    @Getter @Setter
    private String eventDes;
    @Getter @Setter
    private Integer totalVol;
    @Getter @Setter
    private Integer noOfVolJoined;
    @Getter @Setter
    private LocalDate eventDate;
    @Getter @Setter
    private LocalTime eventTime;
    @Getter @Setter
    private String address;
    @Getter @Setter
    private String organizerName;
    @Getter @Setter
    private String contact;
    @Getter @Setter
    private Set<String> requiredSkills;
    @Getter @Setter
    private double score;
}
