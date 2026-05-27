package com.plantcare.service;

import com.plantcare.dto.PlantResponse;
import com.plantcare.model.Plant;
import com.plantcare.repository.PlantRepository;
import com.plantcare.repository.WateringRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final PlantRepository plantRepository;
    private final WateringRepository wateringRepository;
    private final PlantService plantService;

    public ReportService(PlantRepository plantRepository,
                         WateringRepository wateringRepository,
                         PlantService plantService) {
        this.plantRepository = plantRepository;
        this.wateringRepository = wateringRepository;
        this.plantService = plantService;
    }

    public Page<PlantResponse> getNeedsWatering(Pageable pageable) {
        List<Plant> all = plantRepository.findByDeletedAtIsNull(Pageable.unpaged()).getContent();
        List<Plant> needsWater = all.stream()
                .filter(p -> {
                    if (p.getLastWateredAt() == null) return true;
                    return LocalDateTime.now().isAfter(
                            p.getLastWateredAt().plusDays(p.getRecommendedDaysBetweenWater()));
                })
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), needsWater.size());
        List<PlantResponse> page = needsWater.subList(start, end).stream()
                .map(p -> plantService.getById(p.getId()))
                .collect(Collectors.toList());
        return new PageImpl<>(page, pageable, needsWater.size());
    }

    public Map<String, Object> getSummary() {
        long total = plantRepository.findByDeletedAtIsNull(Pageable.unpaged()).getTotalElements();
        long wateredLast7Days = wateringRepository.countByWateredAtAfter(
                LocalDateTime.now().minusDays(7));
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("totalActivePlants", total);
        summary.put("wateringEventsLast7Days", wateredLast7Days);
        return summary;
    }
}