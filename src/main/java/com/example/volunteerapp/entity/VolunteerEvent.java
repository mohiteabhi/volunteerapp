package com.example.volunteerapp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "volunteer_event")
public class VolunteerEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "event_des", columnDefinition = "TEXT")
    private String eventDes;

    @Column(name = "total_vol")
    private Integer totalVol;

    @Column(name = "no_of_vol_joined")
    private Integer noOfVolJoined;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_time")
    private LocalTime eventTime;

    @Column(name = "address", columnDefinition = "VARCHAR(500)")
    private String address;

    @Column(name = "organizer_name")
    private String organizerName;

    @Column(name = "contact")
    private String contact;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "event_skills",
            joinColumns = @JoinColumn(name = "event_id")
    )
    @Column(name = "skill")
    private Set<String> requiredSkills;

    // ————————— Constructors —————————

    public VolunteerEvent() {
        this.isActive = true;
    }

    public VolunteerEvent(Long userId,
                          String cityName,
                          String eventDes,
                          Integer totalVol,
                          Integer noOfVolJoined,
                          LocalDate eventDate,
                          LocalTime eventTime,
                          String address,
                          String organizerName,
                          String contact) {
        this.userId    = userId;
        this.cityName = cityName;
        this.eventDes = eventDes;
        this.totalVol = totalVol;
        this.noOfVolJoined = noOfVolJoined;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.address = address;
        this.organizerName = organizerName;
        this.contact = contact;
    }

    // ————————— Getters and Setters —————————

    public Long getId() {
        return id;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<String> getRequiredSkills() {
        return requiredSkills;
    }
    public void setRequiredSkills(Set<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    // Utility method to check if event is expired
    public boolean isExpired() {
        return this.eventDate != null && this.eventDate.isBefore(LocalDate.now());
    }

    // Method to update active status based on current date
    public void updateActiveStatus() {
        if (isExpired()) {
            this.isActive = false;
        }
    }

    // Optionally override toString(), equals(), hashCode() if needed.
}
