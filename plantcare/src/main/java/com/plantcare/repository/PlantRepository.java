package com.plantcare.repository;

import com.plantcare.model.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    Page<Plant> findByDeletedAtIsNull(Pageable pageable);
    Page<Plant> findByDeletedAtIsNullAndSpecies(String species, Pageable pageable);
    Page<Plant> findByDeletedAtIsNullAndRoom(String room, Pageable pageable);
    Page<Plant> findByDeletedAtIsNullAndSpeciesAndRoom(String species, String room, Pageable pageable);
    Optional<Plant> findByIdAndDeletedAtIsNull(Long id);
}