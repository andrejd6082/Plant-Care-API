package com.plantcare.service;

import com.plantcare.dto.PlantRequest;
import com.plantcare.dto.PlantResponse;
import com.plantcare.exception.ResourceNotFoundException;
import com.plantcare.model.Plant;
import com.plantcare.repository.PlantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlantService {

    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public PlantResponse create(PlantRequest req) {
        Plant plant = new Plant();
        plant.setName(req.getName());
        plant.setSpecies(req.getSpecies());
        plant.setRoom(req.getRoom());
        plant.setRecommendedDaysBetweenWater(req.getRecommendedDaysBetweenWater());
        return toResponse(plantRepository.save(plant));
    }

    public PlantResponse getById(Long id) {
        return toResponse(findActive(id));
    }

    public Page<PlantResponse> getAll(String species, String room, Pageable pageable) {
        Page<Plant> plants;
        if (species != null && room != null) {
            plants = plantRepository.findByDeletedAtIsNullAndSpeciesAndRoom(species, room, pageable);
        } else if (species != null) {
            plants = plantRepository.findByDeletedAtIsNullAndSpecies(species, pageable);
        } else if (room != null) {
            plants = plantRepository.findByDeletedAtIsNullAndRoom(room, pageable);
        } else {
            plants = plantRepository.findByDeletedAtIsNull(pageable);
        }
        return plants.map(this::toResponse);
    }

    public PlantResponse update(Long id, PlantRequest req) {
        Plant plant = findActive(id);
        plant.setName(req.getName());
        plant.setSpecies(req.getSpecies());
        plant.setRoom(req.getRoom());
        plant.setRecommendedDaysBetweenWater(req.getRecommendedDaysBetweenWater());
        return toResponse(plantRepository.save(plant));
    }

    public PlantResponse moveRoom(Long id, String room) {
        Plant plant = findActive(id);
        plant.setRoom(room);
        return toResponse(plantRepository.save(plant));
    }

    public void softDelete(Long id) {
        Plant plant = findActive(id);
        plant.setDeletedAt(LocalDateTime.now());
        plantRepository.save(plant);
    }

    public Plant findActive(Long id) {
        return plantRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plant not found with id: " + id));
    }

    private PlantResponse toResponse(Plant plant) {
        PlantResponse res = new PlantResponse();
        res.setId(plant.getId());
        res.setName(plant.getName());
        res.setSpecies(plant.getSpecies());
        res.setRoom(plant.getRoom());
        res.setRecommendedDaysBetweenWater(plant.getRecommendedDaysBetweenWater());
        res.setLastWateredAt(plant.getLastWateredAt());
        res.setLastFertilisedAt(plant.getLastFertilisedAt());
        res.setCreatedAt(plant.getCreatedAt());
        if (plant.getLastWateredAt() != null && plant.getRecommendedDaysBetweenWater() != null) {
            LocalDateTime nextWatering = plant.getLastWateredAt()
                    .plusDays(plant.getRecommendedDaysBetweenWater());
            res.setNeedsWatering(LocalDateTime.now().isAfter(nextWatering));
        } else {
            res.setNeedsWatering(true);
        }
        return res;
    }
}