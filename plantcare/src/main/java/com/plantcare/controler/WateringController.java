package com.plantcare.controller;

import com.plantcare.dto.FertilisationRequest;
import com.plantcare.dto.WateringRequest;
import com.plantcare.model.FertilisationEvent;
import com.plantcare.model.WateringEvent;
import com.plantcare.service.FertilisationService;
import com.plantcare.service.WateringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plants")
@Tag(name = "Events", description = "Watering and fertilisation events")
public class WateringController {

    private final WateringService wateringService;
    private final FertilisationService fertilisationService;

    public WateringController(WateringService wateringService,
                              FertilisationService fertilisationService) {
        this.wateringService = wateringService;
        this.fertilisationService = fertilisationService;
    }

    @PostMapping("/{id}/waterings")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Log a watering event (enforces 24h rule)")
    public WateringEvent logWatering(@PathVariable Long id,
                                     @Valid @RequestBody(required = false) WateringRequest req) {
        return wateringService.logWatering(id, req);
    }

    @GetMapping("/{id}/waterings")
    @Operation(summary = "Get watering history for a plant")
    public Page<WateringEvent> getWateringHistory(@PathVariable Long id, Pageable pageable) {
        return wateringService.getHistory(id, pageable);
    }

    @PostMapping("/{id}/fertilisations")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Log a fertilisation event")
    public FertilisationEvent logFertilisation(@PathVariable Long id,
                                               @Valid @RequestBody FertilisationRequest req) {
        return fertilisationService.log(id, req);
    }

    @GetMapping("/{id}/fertilisations")
    @Operation(summary = "Get fertilisation history for a plant")
    public Page<FertilisationEvent> getFertilisationHistory(@PathVariable Long id,
                                                            Pageable pageable) {
        return fertilisationService.getHistory(id, pageable);
    }
}