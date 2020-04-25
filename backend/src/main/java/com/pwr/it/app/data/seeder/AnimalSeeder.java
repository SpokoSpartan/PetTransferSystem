package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.Animal;
import com.pwr.it.app.data.domain.Race;
import com.pwr.it.app.data.domain.Species;
import com.pwr.it.app.data.domain.Status;
import com.pwr.it.app.data.domain.TreatmentHistory;
import com.pwr.it.app.data.domain.User;
import com.pwr.it.app.data.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Set;

@Singleton
@RequiredArgsConstructor
public class AnimalSeeder {

    private final AnimalRepository animalRepository;

    public Animal initPersianCat(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories){
        Animal persianCat = Animal.builder()
                .name("Miki")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("Miki is an approx. 2-year-old cat who came to us as found. " +
                        "He was abandoned in a container on one of Wroclaw's streets. " +
                        "He is secretive and shy at the moment, but will probably open over time.")
                .imageUrl("http://res.cloudinary.com/drkqvtiuz/image/upload/v1587815626/i05qn8oq4evpucecf2wq.jpg")
                .birthDate(getDateBackward(2))
                .sex("female")
                .shelterJoinDate(getDateBackward(1))
                .user(user)
                .build();
        persianCat.setSterilised(true);
        treatmentHistories.forEach(treatmentHistory -> persianCat.addTreatmentHistory(treatmentHistory));
        return initAnimal(persianCat);
    }

    public Animal initSiameseCat(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal siameseCat = Animal.builder()
                .name("Pampuch")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("Pampuch is a cute 4 year old pet. he still wants to stroke petticoat " +
                        "and demands attention from the owner. You can take him in your arms and " +
                        "rub him on the stomach - everything suits him! :) Pampuszek likes to eat " +
                        "(which you can see a little), so his heart will get even faster through the stomach")
                .imageUrl("http://res.cloudinary.com/drkqvtiuz/image/upload/v1587815744/dul2iyhw6mypepmpgirh.jpg")
                .birthDate(getDateBackward(4))
                .sex("male")
                .shelterJoinDate(getDateBackward(2))
                .user(user)
                .build();
        siameseCat.setSterilised(false);
        treatmentHistories.forEach(treatmentHistory -> siameseCat.addTreatmentHistory(treatmentHistory));
        return initAnimal(siameseCat);
    }

    public Animal initHuskyDog(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal huskyDog = Animal.builder()
                .name("Mała")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("Donka is about 10 years old and spent her whole life on a chain. " +
                        "She was very inaccessible. Currently, however, he is a much nicer " +
                        "dog who likes to go for walks. Once she meets someone, she demands attention and stroking")
                .imageUrl("http://res.cloudinary.com/drkqvtiuz/image/upload/v1587815804/ao5f5fvz9abfa0xrbka5.jpg")
                .birthDate(getDateBackward(10))
                .sex("female")
                .shelterJoinDate(getDateBackward(1))
                .user(user)
                .build();
        huskyDog.setSterilised(false);
        treatmentHistories.forEach(treatmentHistory -> huskyDog.addTreatmentHistory(treatmentHistory));
        return initAnimal(huskyDog);
    }

    public Animal initBulldog(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal bulldog = Animal.builder()
                .name("Donka")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("Little is 7 years old. She was returned from adoption - after leaving " +
                        "the guardian's (man's) home she stopped listening to the woman. " +
                        "She was aggressive, guarding. The small one is quite independent. " +
                        "She is a VERY intelligent dog - in her eyes you can see how smart and clever pooch ;)")
                .imageUrl("http://res.cloudinary.com/drkqvtiuz/image/upload/v1587815886/secqanzw5p8mbqws2zed.jpg")
                .birthDate(getDateBackward(10))
                .sex("female")
                .shelterJoinDate(getDateBackward(5))
                .user(user)
                .build();
        bulldog.setSterilised(false);
        treatmentHistories.forEach(treatmentHistory -> bulldog.addTreatmentHistory(treatmentHistory));
        return initAnimal(bulldog);
    }

    public Animal initRoyalPython(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal royalPython = Animal.builder()
                .name("Snaker")
                .species(species)
                .race(race)
                .statuses(statuses)
                .imageUrl("http://res.cloudinary.com/drkqvtiuz/image/upload/v1587816007/lntd5e9mjsvdd3xpbzjm.jpg")
                .birthDate(getDateBackward(2))
                .sex("female")
                .shelterJoinDate(getDateBackward(1))
                .user(user)
                .build();
        royalPython.setSterilised(false);
        treatmentHistories.forEach(treatmentHistory -> royalPython.addTreatmentHistory(treatmentHistory));
        return initAnimal(royalPython);
    }
    
    private Date getDateBackward(int years) {
        return new DateTime().minusYears(years).toDate();
    }

    @Transactional
    private Animal initAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

}
