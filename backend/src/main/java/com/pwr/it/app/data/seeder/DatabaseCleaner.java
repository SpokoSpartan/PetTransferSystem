package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.repository.*;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@RequiredArgsConstructor
public class DatabaseCleaner {

    private final RaceRepository raceRepository;
    private final SpeciesRepository speciesRepository;
    private final StatusRepository statusRepository;
    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final TreatmentHistoryRepository treatmentHistoryRepository;
    private final AnimalRepository animalRepository;

    @Transactional
    public void clean() {
        statusRepository.deleteAll();
        treatmentHistoryRepository.deleteAll();
        animalRepository.deleteAll();
        userRepository.deleteAll();
        organizationRepository.deleteAll();
        speciesRepository.deleteAll();
        raceRepository.deleteAll();
    }
}
