package com.example.volunteerapp.repository;

import com.example.volunteerapp.entity.VolunteerEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VolunteerEventRepository extends JpaRepository<VolunteerEvent, Long> {
    // We inherit: save(), findAll(), findById(), deleteById(), etc.
    List<VolunteerEvent> findByCityName(String cityName);
    List<VolunteerEvent> findByUserId(Long userId);
    List<VolunteerEvent> findByEventDateAfter(LocalDate date);

    // New methods for isActive functionality
    List<VolunteerEvent> findByIsActiveTrue();
    List<VolunteerEvent> findByIsActiveFalse();
    List<VolunteerEvent> findByCityNameAndIsActiveTrue(String cityName);
    List<VolunteerEvent> findByUserIdAndIsActiveTrue(Long userId);

    // Method to find events that should be deactivated
    @Query("SELECT e FROM VolunteerEvent e WHERE e.eventDate < :currentDate AND e.isActive = true")
    List<VolunteerEvent> findExpiredActiveEvents(@Param("currentDate") LocalDate currentDate);

    // Bulk update for expired events
    @Modifying
    @Query("UPDATE VolunteerEvent e SET e.isActive = false WHERE e.eventDate < :currentDate AND e.isActive = true")
    int deactivateExpiredEvents(@Param("currentDate") LocalDate currentDate);

}
