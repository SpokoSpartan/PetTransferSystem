package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.Species;
import com.pwr.it.app.data.repository.SpeciesRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
@RequiredArgsConstructor
public class SpeciesService {

    private final SpeciesRepository speciesRepository;

    @Transactional
    public List<String> getAllSpecies() {
        return StreamSupport.stream(speciesRepository.findAll().spliterator(), false)
                .map(Species::getName)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

}
