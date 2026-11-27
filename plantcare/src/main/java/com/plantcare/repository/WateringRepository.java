package com.plantcare.repository;

import com.plantcare.model.WateringEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface WateringRepository extends JpaRepository<WateringEvent, Long> {
    Page<WateringEvent> findByPlantId(Long plantId, Pageable pageable);
    Optional<WateringEvent> findTopByPlantIdOrderByWateredAtDesc(Long plantId);
    long countByWateredAtAfter(LocalDateTime since);
    boolean existsByPlantIdAndWateredAtBetween(Long plantId, LocalDateTime from, LocalDateTime to);
}