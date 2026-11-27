package com.plantcare.dto;

import jakarta.validation.constraints.Size;

import java.time.Instant;

public class WateringRequest {

    private String notes;

    // Who watered the plant, e.g. "Mom". Optional.
    @Size(max = 40, message = "Watered by must be at most 40 characters")
    private String wateredBy;

    // When the plant was watered, e.g. "2026-11-27T08:30:00Z". Optional; defaults to now.
    // Lets a client that was offline send the real time later.
    private Instant wateredAt;

    public Instant getWateredAt() { return wateredAt; }
    public void setWateredAt(Instant wateredAt) { this.wateredAt = wateredAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getWateredBy() { return wateredBy; }
    public void setWateredBy(String wateredBy) { this.wateredBy = wateredBy; }
}
