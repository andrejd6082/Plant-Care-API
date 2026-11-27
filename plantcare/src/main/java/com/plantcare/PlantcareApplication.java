package com.plantcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class PlantcareApplication {
    public static void main(String[] args) {
        // All dates are stored and returned in UTC, wherever the server runs.
        // Clients convert to local time for display.
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(PlantcareApplication.class, args);
    }
}
