package com.example.volunteerapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EventSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(EventSchedulerService.class);

    @Autowired
    private VolunteerEventService volunteerEventService;

    // Run every day at 2 AM
    @Scheduled(cron = "0 0 2 * * ?")
    public void deactivateExpiredEvents() {
        logger.info("Starting scheduled task to deactivate expired events");
        try {
            int deactivatedCount = volunteerEventService.bulkDeactivateExpiredEvents();
            logger.info("Successfully deactivated {} expired events", deactivatedCount);
        } catch (Exception e) {
            logger.error("Error occurred while deactivating expired events", e);
        }
    }

    // Optional: Run every hour during business hours (9 AM to 6 PM)
    @Scheduled(cron = "0 0 9-18 * * ?")
    public void updateExpiredEventsHourly() {
        logger.info("Hourly update of expired events status");
        try {
            volunteerEventService.updateExpiredEvents();
        } catch (Exception e) {
            logger.error("Error occurred during hourly expired events update", e);
        }
    }
}
