package com.plantcare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fertilisation_events")
public class FertilisationEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    private LocalDateTime fertilisedAt = LocalDateTime.now();
    private String productUsed;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Plant getPlant() { return plant; }
    public void setPlant(Plant plant) { this.plant = plant; }

    public LocalDateTime getFertilisedAt() { return fertilisedAt; }
    public void setFertilisedAt(LocalDateTime fertilisedAt) { this.fertilisedAt = fertilisedAt; }

    public String getProductUsed() { return productUsed; }
    public void setProductUsed(String productUsed) { this.productUsed = productUsed; }
}