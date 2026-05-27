package com.plantcare.dto;

import java.time.LocalDateTime;

public class PlantResponse {

    private Long id;
    private String name;
    private String species;
    private String room;
    private Integer recommendedDaysBetweenWater;
    private LocalDateTime lastWateredAt;
    private LocalDateTime lastFertilisedAt;
    private LocalDateTime createdAt;
    private boolean needsWatering;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public LocalDateTime getLastWateredAt() { return lastWateredAt; }
    public void setLastWateredAt(LocalDateTime lastWateredAt) { this.lastWateredAt = lastWateredAt; }

    public LocalDateTime getLastFertilisedAt() { return lastFertilisedAt; }
    public void setLastFertilisedAt(LocalDateTime lastFertilisedAt) { this.lastFertilisedAt = lastFertilisedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isNeedsWatering() { return needsWatering; }
    public void setNeedsWatering(boolean needsWatering) { this.needsWatering = needsWatering; }
}