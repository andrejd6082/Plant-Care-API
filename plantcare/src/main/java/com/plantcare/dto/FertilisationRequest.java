package com.plantcare.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public class FertilisationRequest {

    @NotBlank(message = "Product used is required")
    private String productUsed;

    // When the plant was fertilised. Optional; defaults to now.
    private Instant fertilisedAt;

    public Instant getFertilisedAt() { return fertilisedAt; }
    public void setFertilisedAt(Instant fertilisedAt) { this.fertilisedAt = fertilisedAt; }

    public String getProductUsed() { return productUsed; }
    public void setProductUsed(String productUsed) { this.productUsed = productUsed; }
}