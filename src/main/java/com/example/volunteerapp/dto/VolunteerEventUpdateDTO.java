package com.example.volunteerapp.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class VolunteerEventUpdateDTO {

    @NotBlank(message = "City name must not be blank")
    private String cityName;

    @NotBlank(message = "Event description must not be blank")
    private String eventDes;

    @NotNull(message = "Total volunteers is required")
    @Min(value = 0, message = "Total volunteers cannot be negative")
    private Integer totalVol;

    @NotNull(message = "Number of volunteers joined is required")
    @Min(value = 0, message = "Number of volunteers joined cannot be negative")
    private Integer noOfVolJoined;

    @NotNull(message = "Event date is required")
    private LocalDate eventDate;

    @NotNull(message = "Event time is required")
    private LocalTime eventTime;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @NotBlank(message = "Organizer name must not be blank")
    private String organizerName;

    @NotBlank(message = "Contact must not be blank")
    private String contact;

    // ————————— Getters and Setters —————————

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getEventDes() {
        return eventDes;
    }

    public void setEventDes(String eventDes) {
        this.eventDes = eventDes;
    }

    public Integer getTotalVol() {
        return totalVol;
    }

    public void setTotalVol(Integer totalVol) {
        this.totalVol = totalVol;
    }

    public Integer getNoOfVolJoined() {
        return noOfVolJoined;
    }

    public void setNoOfVolJoined(Integer noOfVolJoined) {
        this.noOfVolJoined = noOfVolJoined;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
