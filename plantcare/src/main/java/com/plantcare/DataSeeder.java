package com.plantcare;

import com.plantcare.model.FertilisationEvent;
import com.plantcare.model.Plant;
import com.plantcare.model.WateringEvent;
import com.plantcare.repository.FertilisationRepository;
import com.plantcare.repository.PlantRepository;
import com.plantcare.repository.WateringRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final PlantRepository plantRepository;
    private final WateringRepository wateringRepository;
    private final FertilisationRepository fertilisationRepository;

    public DataSeeder(PlantRepository plantRepository,
                      WateringRepository wateringRepository,
                      FertilisationRepository fertilisationRepository) {
        this.plantRepository = plantRepository;
        this.wateringRepository = wateringRepository;
        this.fertilisationRepository = fertilisationRepository;
    }

    @Override
    public void run(String... args) {

        // --- Plants ---
        Plant monstera = plant("Monstera", "Monstera deliciosa", "Living Room", 7);
        Plant pothos   = plant("Pothos",   "Epipremnum aureum",  "Kitchen",      5);
        Plant cactus   = plant("Cactus",   "Echinocactus grusonii", "Bedroom",  30);
        Plant fern     = plant("Boston Fern", "Nephrolepis exaltata", "Bathroom", 3);
        Plant snake    = plant("Snake Plant", "Sansevieria trifasciata", "Office", 14);

        Plant dead     = plant("Dead Ivy",  "Hedera helix",       "Garage",      7);
        dead.setDeletedAt(LocalDateTime.now().minusDays(2));

        List<Plant> saved = plantRepository.saveAll(
                List.of(monstera, pothos, cactus, fern, snake, dead));

        monstera = saved.get(0);
        pothos   = saved.get(1);
        cactus   = saved.get(2);
        fern     = saved.get(3);
        snake    = saved.get(4);



        addWatering(monstera, LocalDateTime.now().minusDays(8), "Soil was very dry");
        addWatering(monstera, LocalDateTime.now().minusDays(15), null);
        monstera.setLastWateredAt(LocalDateTime.now().minusDays(8));


        addWatering(pothos, LocalDateTime.now().minusHours(2), "Leaves looked droopy");
        pothos.setLastWateredAt(LocalDateTime.now().minusHours(2));


        addWatering(cactus, LocalDateTime.now().minusDays(5), null);
        cactus.setLastWateredAt(LocalDateTime.now().minusDays(5));


        addWatering(fern, LocalDateTime.now().minusDays(4), "Misted the leaves too");
        addWatering(fern, LocalDateTime.now().minusDays(7), null);
        fern.setLastWateredAt(LocalDateTime.now().minusDays(4));


        plantRepository.saveAll(List.of(monstera, pothos, cactus, fern));


        addFertilisation(monstera, "Osmocote Slow Release", LocalDateTime.now().minusDays(30));
        addFertilisation(pothos,   "Liquid Miracle-Gro",    LocalDateTime.now().minusDays(14));
        addFertilisation(fern,     "Peters 20-20-20",       LocalDateTime.now().minusDays(60));
        monstera.setLastFertilisedAt(LocalDateTime.now().minusDays(30));
        pothos.setLastFertilisedAt(LocalDateTime.now().minusDays(14));
        fern.setLastFertilisedAt(LocalDateTime.now().minusDays(60));

        plantRepository.saveAll(List.of(monstera, pothos, fern));

        System.out.println(">>> DataSeeder: seeded 5 active plants, 1 soft-deleted, " +
                "7 watering events, 3 fertilisation events.");
    }

    private Plant plant(String name, String species, String room, int intervalDays) {
        Plant p = new Plant();
        p.setName(name);
        p.setSpecies(species);
        p.setRoom(room);
        p.setRecommendedDaysBetweenWater(intervalDays);
        return p;
    }

    private void addWatering(Plant plant, LocalDateTime at, String notes) {
        WateringEvent e = new WateringEvent();
        e.setPlant(plant);
        e.setWateredAt(at);
        e.setNotes(notes);
        wateringRepository.save(e);
    }

    private void addFertilisation(Plant plant, String product, LocalDateTime at) {
        FertilisationEvent e = new FertilisationEvent();
        e.setPlant(plant);
        e.setProductUsed(product);
        e.setFertilisedAt(at);
        fertilisationRepository.save(e);
    }
}