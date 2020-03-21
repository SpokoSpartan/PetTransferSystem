package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.AnimalStatus;
import com.pwr.it.app.data.domain.Race;
import com.pwr.it.app.data.domain.Species;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;

import javax.inject.Singleton;
import java.util.Date;

@Singleton
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationEventListener<ServerStartupEvent> {

    private final RaceSeeder raceSeeder;
    private final SpeciesSeeder speciesSeeder;
    private final StatusSeeder statusSeeder;
    private final AnimalSeeder animalSeeder;

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        Race persianCat = raceSeeder.initPersianCat();
        Race siameseCat = raceSeeder.initSiameseCat();
        Race husky = raceSeeder.initSiberianHusky();
        Race bulldog = raceSeeder.initBulldog();
        Race royalPython = raceSeeder.initRoyalPython();

        Species dog = speciesSeeder.initDog();
        Species cat = speciesSeeder.initCat();
        Species snake = speciesSeeder.initSnake();

        statusSeeder.initAnimalStatuses(DateTime.now().minusDays(128).toDate());


        //Animal animal = animalSeeder.initAnimal("Chester", cat, persianCat, null, "Descr", dateBackwardYears(2),)

    }

    private Date dateBackwardYears(int years) {
        return DateTime.now().minusYears(years).toDate();
    }

}
