package com.plantcare.dto;

import jakarta.validation.constraints.*;

public class PlantRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Species is required")
    private String species;

    @NotBlank(message = "Room is required")
    private String room;

    @NotNull(message = "Recommended days between water is required")
    @Min(value = 1, message = "Must be at least 1 day")
    @Max(value = 365, message = "Cannot exceed 365 days")
    private Integer recommendedDaysBetweenWater;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public Integer getRecommendedDaysBetweenWater() { return recommendedDaysBetweenWater; }
    public void setRecommendedDaysBetweenWater(Integer recommendedDaysBetweenWater) {
        this.recommendedDaysBetweenWater = recommendedDaysBetweenWater;
    }
}