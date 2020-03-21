package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.Animal;
import com.pwr.it.app.data.domain.Race;
import com.pwr.it.app.data.domain.Species;
import com.pwr.it.app.data.domain.Status;
import com.pwr.it.app.data.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.Date;

@Singleton
@RequiredArgsConstructor
public class AnimalSeeder {

    private final AnimalRepository animalRepository;

    public Animal initAnimal(String name, Species species, Race race, Status status,
                             String description, Date birthDate, String sex, boolean sterilised, Date shelterJoinDate){
        Animal animal = Animal.builder()
                .name(name)
                .species(species)
                .race(race)
                .statuses(Collections.singleton(status))
                .description(description)
                .birthDate(birthDate)
                .sex(sex)
                .sterilised(sterilised)
                .shelterJoinDate(shelterJoinDate)
                .build();
        return animalRepository.save(animal);
    }

}
