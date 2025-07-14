package com.example.volunteerapp.controller;

import com.example.volunteerapp.dto.SuggestedEventDTO;
import com.example.volunteerapp.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<SuggestedEventDTO>> getRecommendations(@PathVariable Long userId) {
        List<SuggestedEventDTO> list = recommendationService.suggestEventsForUser(userId);
        return ResponseEntity.ok(list);
    }
}
