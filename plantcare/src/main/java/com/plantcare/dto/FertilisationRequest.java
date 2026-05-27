package com.plantcare.dto;

import jakarta.validation.constraints.NotBlank;

public class FertilisationRequest {

    @NotBlank(message = "Product used is required")
    private String productUsed;

    public String getProductUsed() { return productUsed; }
    public void setProductUsed(String productUsed) { this.productUsed = productUsed; }
}