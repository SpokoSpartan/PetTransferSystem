package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.Race;
import com.pwr.it.app.data.repository.RaceRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
@RequiredArgsConstructor
public class RaceService {

    private final RaceRepository raceRepository;

    @Transactional
    public List<String> getAllRaces() {
        return StreamSupport.stream(raceRepository.findAll().spliterator(), false)
                .map(Race::getName)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    public Race getSavedRaceByName(String name) {
        Optional<Race> race = raceRepository.findByName(name);
        return race.orElseGet(() -> raceRepository.save(new Race(name)));
    }

}
