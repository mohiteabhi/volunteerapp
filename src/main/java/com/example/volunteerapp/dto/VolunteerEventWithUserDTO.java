package com.example.volunteerapp.dto;

import com.example.volunteerapp.entity.UserInfo;
import com.example.volunteerapp.entity.VolunteerEvent;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class VolunteerEventWithUserDTO {
    private Long id;
    private UserDTO user;
    private String cityName;
    private String eventDes;
    private Integer totalVol;
    private Integer noOfVolJoined;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String address;
    private String organizerName;
    private String contact;
    private Set<String> requiredSkills;

    public VolunteerEventWithUserDTO(VolunteerEvent event, UserInfo user) {
        // Event fields
        this.id = event.getId();
        this.cityName = event.getCityName();
        this.eventDes = event.getEventDes();
        this.totalVol = event.getTotalVol();
        this.noOfVolJoined = event.getNoOfVolJoined();
        this.eventDate = event.getEventDate();
        this.eventTime = event.getEventTime();
        this.address = event.getAddress();
        this.organizerName = event.getOrganizerName();
        this.contact = event.getContact();
        this.requiredSkills = event.getRequiredSkills();

        // User fields
        this.user = new UserDTO(
                user.getUserId(),
                user.getFullName(),
                user.getAge(),
                user.getEmail(),
                user.getAddress(),
                user.getCityName(),
                user.getContactNo(),
                user.getSkills()
        );
    }
}
