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

        wateringRepository.findTopByPlantIdOrderByWateredAtDesc(plantId)
                .ifPresent(last -> {
                    if (last.getWateredAt().isAfter(LocalDateTime.now().minusHours(24))) {
                        throw new BusinessRuleException(
                                "Plant was already watered within the last 24 hours. Try again later.");
                    }
                });

        WateringEvent event = new WateringEvent();
        event.setPlant(plant);
        event.setNotes(req != null ? req.getNotes() : null);

        WateringEvent saved = wateringRepository.save(event);
        plant.setLastWateredAt(saved.getWateredAt());
        plantRepository.save(plant);
        return saved;
    }

    public Page<WateringEvent> getHistory(Long plantId, Pageable pageable) {
        plantService.findActive(plantId);
        return wateringRepository.findByPlantId(plantId, pageable);
    }
}