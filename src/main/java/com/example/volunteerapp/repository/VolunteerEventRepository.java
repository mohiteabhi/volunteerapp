package com.example.volunteerapp.repository;

import com.example.volunteerapp.entity.VolunteerEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerEventRepository extends JpaRepository<VolunteerEvent, Long> {
    // We inherit: save(), findAll(), findById(), deleteById(), etc.
    List<VolunteerEvent> findByCityName(String cityName);
    List<VolunteerEvent> findByUserId(Long userId);

}
