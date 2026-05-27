package com.plantcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "watering_events")
public class WateringEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    private LocalDateTime wateredAt = LocalDateTime.now();
    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Plant getPlant() { return plant; }
    public void setPlant(Plant plant) { this.plant = plant; }

    public LocalDateTime getWateredAt() { return wateredAt; }
    public void setWateredAt(LocalDateTime wateredAt) { this.wateredAt = wateredAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}