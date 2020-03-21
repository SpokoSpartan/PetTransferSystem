package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.Race;
import com.pwr.it.app.data.repository.RaceRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@RequiredArgsConstructor
public class RaceSeeder {

    private final RaceRepository raceRepository;

    public Race initPersianCat() {
        Race persianCat = new Race("Persian cat");
        return initRace(persianCat);
    }

    public Race initSiameseCat() {
        Race siameseCat = new Race("Siamese cat");
        return initRace(siameseCat);
    }

    public Race initSiberianHusky() {
        Race siberianHusky = new Race("Siberian Husky");
        return initRace(siberianHusky);
    }

    public Race initBulldog() {
        Race bulldog = new Race("Bulldog");
        return initRace(bulldog);
    }

    public Race initRoyalPython() {
        Race royalPython = new Race("Royal python");
        return initRace(royalPython);
    }

    @Transactional
    private Race initRace(Race race) {
        return raceRepository.save(race);
    }

}
