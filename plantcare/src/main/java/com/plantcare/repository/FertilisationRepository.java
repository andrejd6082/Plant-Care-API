package com.plantcare.repository;

import com.plantcare.model.FertilisationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FertilisationRepository extends JpaRepository<FertilisationEvent, Long> {
    Page<FertilisationEvent> findByPlantId(Long plantId, Pageable pageable);
}