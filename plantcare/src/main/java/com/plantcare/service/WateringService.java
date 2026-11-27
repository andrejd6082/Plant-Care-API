package com.plantcare.service;

import com.plantcare.dto.WateringRequest;
import com.plantcare.exception.BusinessRuleException;
import com.plantcare.model.Plant;
import com.plantcare.model.WateringEvent;
import com.plantcare.repository.PlantRepository;
import com.plantcare.repository.WateringRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WateringService {

    private final WateringRepository wateringRepository;
    private final PlantService plantService;
    private final PlantRepository plantRepository;

    public WateringService(WateringRepository wateringRepository,
                           PlantService plantService,
                           PlantRepository plantRepository) {
        this.wateringRepository = wateringRepository;
        this.plantService = plantService;
        this.plantRepository = plantRepository;
    }

    public WateringEvent logWatering(Long plantId, WateringRequest req) {
        Plant plant = plantService.findActive(plantId);

        LocalDateTime wateredAt = EventTimes.resolve(req != null ? req.getWateredAt() : null);

        // 24-hour rule: no other watering within 24 hours before or after this one.
        // Checking both sides matters when an offline client sends an older time.
        if (wateringRepository.existsByPlantIdAndWateredAtBetween(
                plantId, wateredAt.minusHours(24), wateredAt.plusHours(24))) {
            throw new BusinessRuleException(
                    "Plant was already watered within 24 hours of this time.");
        }

        WateringEvent event = new WateringEvent();
        event.setPlant(plant);
        event.setWateredAt(wateredAt);
        event.setNotes(req != null ? req.getNotes() : null);
        event.setWateredBy(req != null ? req.getWateredBy() : null);

        WateringEvent saved = wateringRepository.save(event);
        // A late-synced older watering must not replace a newer "last watered".
        if (plant.getLastWateredAt() == null || saved.getWateredAt().isAfter(plant.getLastWateredAt())) {
            plant.setLastWateredAt(saved.getWateredAt());
            plant.setLastWateredBy(saved.getWateredBy());
            plantRepository.save(plant);
        }
        return saved;
    }

    public Page<WateringEvent> getHistory(Long plantId, Pageable pageable) {
        plantService.findActive(plantId);
        return wateringRepository.findByPlantId(plantId, pageable);
    }
}