package com.plantcare.dto;

import jakarta.validation.constraints.Size;

public class WateringRequest {

    private String notes;

    // Who watered the plant, e.g. "Mom". Optional.
    @Size(max = 40, message = "Watered by must be at most 40 characters")
    private String wateredBy;

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getWateredBy() { return wateredBy; }
    public void setWateredBy(String wateredBy) { this.wateredBy = wateredBy; }
}
