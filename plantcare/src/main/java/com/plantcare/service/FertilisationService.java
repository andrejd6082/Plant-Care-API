package com.plantcare.service;

import com.plantcare.dto.FertilisationRequest;
import com.plantcare.model.FertilisationEvent;
import com.plantcare.model.Plant;
import com.plantcare.repository.FertilisationRepository;
import com.plantcare.repository.PlantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FertilisationService {

    private final FertilisationRepository fertilisationRepository;
    private final PlantService plantService;
    private final PlantRepository plantRepository;

    public FertilisationService(FertilisationRepository fertilisationRepository,
                                PlantService plantService,
                                PlantRepository plantRepository) {
        this.fertilisationRepository = fertilisationRepository;
        this.plantService = plantService;
        this.plantRepository = plantRepository;
    }

    public FertilisationEvent log(Long plantId, FertilisationRequest req) {
        Plant plant = plantService.findActive(plantId);
        FertilisationEvent event = new FertilisationEvent();
        event.setPlant(plant);
        event.setProductUsed(req.getProductUsed());
        event.setFertilisedAt(EventTimes.resolve(req.getFertilisedAt()));
        FertilisationEvent saved = fertilisationRepository.save(event);
        if (plant.getLastFertilisedAt() == null || saved.getFertilisedAt().isAfter(plant.getLastFertilisedAt())) {
            plant.setLastFertilisedAt(saved.getFertilisedAt());
            plantRepository.save(plant);
        }
        return saved;
    }

    public Page<FertilisationEvent> getHistory(Long plantId, Pageable pageable) {
        plantService.findActive(plantId);
        return fertilisationRepository.findByPlantId(plantId, pageable);
    }
}