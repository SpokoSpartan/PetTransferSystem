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

    public Species initChameleon() {
        Species chameleon = new Species("chameleon");
        return initSpecies(chameleon);
    }

    public Species initEublepharis() {
        Species eublepharis = new Species("eublepharis");
        return initSpecies(eublepharis);
    }

    public Species initRabbit() {
        Species rabbit = new Species("rabbit");
        return initSpecies(rabbit);
    }

    public Species initCacatua() {
        Species cacatua = new Species("cacatua");
        return initSpecies(cacatua);
    }

    public Species initSugarGlider() {
        Species sugarGlider = new Species("sugar glider");
        return initSpecies(sugarGlider);
    }

    public Species initWeasel() {
        Species weasel = new Species("weasel");
        return initSpecies(weasel);
    }

    public Species initTortoise() {
        Species tortoise = new Species("tortoise");
        return initSpecies(tortoise);
    }

    @Transactional
    private Species initSpecies(Species species) {
        return speciesRepository.save(species);
    }

}
