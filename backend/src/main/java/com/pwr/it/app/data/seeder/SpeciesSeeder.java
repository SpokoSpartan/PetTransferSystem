package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.Species;
import com.pwr.it.app.data.repository.SpeciesRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@RequiredArgsConstructor
public class SpeciesSeeder {

    private final SpeciesRepository speciesRepository;

    public Species initCat() {
        Species cat = new Species("cat");
        return initSpecies(cat);
    }

    public Species initDog() {
        Species dog = new Species("dog");
        return initSpecies(dog);
    }

    public Species initSnake() {
        Species snake = new Species("snake");
        return initSpecies(snake);
    }

    @Transactional
    private Species initSpecies(Species species) {
        return speciesRepository.save(species);
    }

}
