package com.plantcare.controller;

import com.plantcare.dto.PlantRequest;
import com.plantcare.dto.PlantResponse;
import com.plantcare.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plants")
@Tag(name = "Plants", description = "CRUD for houseplants")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new plant")
    public PlantResponse create(@Valid @RequestBody PlantRequest req) {
        return plantService.create(req);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a plant by ID")
    public PlantResponse getById(@PathVariable Long id) {
        return plantService.getById(id);
    }

    @GetMapping
    @Operation(summary = "List all plants, filter by species and/or room")
    public Page<PlantResponse> getAll(
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String room,
            Pageable pageable) {
        return plantService.getAll(species, room, pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a plant")
    public PlantResponse update(@PathVariable Long id,
                                @Valid @RequestBody PlantRequest req) {
        return plantService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Soft-delete a plant")
    public void delete(@PathVariable Long id) {
        plantService.softDelete(id);
    }

    @PatchMapping("/{id}/room")
    @Operation(summary = "Move a plant to a different room")
    public PlantResponse moveRoom(@PathVariable Long id,
                                  @RequestParam String room) {
        return plantService.moveRoom(id, room);
    }
}