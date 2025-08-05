package com.example.volunteerapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VolunteerappApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolunteerappApplication.class, args);
	}

}
