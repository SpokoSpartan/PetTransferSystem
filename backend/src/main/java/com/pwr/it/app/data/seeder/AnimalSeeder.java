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


    public Animal initVeiledChameleon(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal veiledChameleon = Animal.builder()
                .name("Nairobi")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("malnourished at young age, requires experienced handle")
                .birthDate(getDateBackward(1))
                .sex("female")
                .shelterJoinDate(getDateBackward(0))
                .user(user)
                .build();
        veiledChameleon.setSterilised(false);
        treatmentHistories.forEach(treatmentHistory -> veiledChameleon.addTreatmentHistory(treatmentHistory));
        return initAnimal(veiledChameleon);
    }

    public Animal initLeopardGecko(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal leopardGecko = Animal.builder()
                .name("Armageddon")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("morph: classic")
                .imageUrl("https://44536.smushcdn.com/1090522/assets/2019/12/600695.jpg?lossy=1&strip=1&webp=1")
                .birthDate(getDateBackward(2))
                .sex("male")
                .shelterJoinDate(getDateBackward(1))
                .user(user)
                .build();
        leopardGecko.setSterilised(false);
        treatmentHistories.forEach(treatmentHistory -> leopardGecko.addTreatmentHistory(treatmentHistory));
        return initAnimal(leopardGecko);
    }

    public Animal initHollandLopRabbit(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal hollandLopRabbit = Animal.builder()
                .name("Antonio")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("small, brown/red medium fur, bit overweight - requires a specialistic diet, gets along with other rabbits")
                .birthDate(getDateBackward(5))
                .sex("male")
                .shelterJoinDate(getDateBackward(1))
                .user(user)
                .build();
        hollandLopRabbit.setSterilised(true);
        treatmentHistories.forEach(treatmentHistory -> hollandLopRabbit.addTreatmentHistory(treatmentHistory));
        return initAnimal(hollandLopRabbit);
    }

    public Animal initSulphurCrestedCockatoo(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal sulphurCrestedCockatoo = Animal.builder()
                .name("Beaky")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("short, like on adoption sites) hand-reared, know a few words and phrases, friendly but can bite, requires an experienced owner")
                .sex("male")
                .shelterJoinDate(getDateBackward(1))
                .user(user)
                .build();
        sulphurCrestedCockatoo.setSterilised(false);
        treatmentHistories.forEach(treatmentHistory -> sulphurCrestedCockatoo.addTreatmentHistory(treatmentHistory));
        return initAnimal(sulphurCrestedCockatoo);
    }

    public Animal initFirstSugarGlider(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal sugarGlider = Animal.builder()
                .name("Bonnie")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("bonded with Clyde, for adoption only together; friendly but doesn't like to be held or pet")
                .birthDate(getDateBackward(2))
                .sex("Female")
                .shelterJoinDate(getDateBackward(0))
                .user(user)
                .build();
        sugarGlider.setSterilised(true);
        treatmentHistories.forEach(treatmentHistory -> sugarGlider.addTreatmentHistory(treatmentHistory));
        return initAnimal(sugarGlider);
    }

    public Animal initSecondSugarGlider(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal sugarGlider = Animal.builder()
                .name("Clyde")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("bonded with Bonnie, for adoption only together; friendly but doesn't like to be held or pet")
                .birthDate(getDateBackward(2))
                .sex("male")
                .shelterJoinDate(getDateBackward(0))
                .user(user)
                .build();
        sugarGlider.setSterilised(true);
        treatmentHistories.forEach(treatmentHistory -> sugarGlider.addTreatmentHistory(treatmentHistory));
        return initAnimal(sugarGlider);
    }

    public Animal initLionheadRabbit(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal lionheadRabbit = Animal.builder()
                .name("Kazik")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("medium-sized, white & brown medium fur; requires regular dental care")
                .birthDate(getDateBackward(3))
                .sex("female")
                .shelterJoinDate(getDateBackward(1))
                .user(user)
                .build();
        lionheadRabbit.setSterilised(true);
        treatmentHistories.forEach(treatmentHistory -> lionheadRabbit.addTreatmentHistory(treatmentHistory));
        return initAnimal(lionheadRabbit);
    }

    public Animal initNetherlandDwarfRabbit(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal netherlandDwarfRabbit = Animal.builder()
                .name("Black Jack")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("small, black fur; dominant in a group, prone to attacking other rabbits")
                .birthDate(getDateBackward(4))
                .sex("male")
                .shelterJoinDate(getDateBackward(1))
                .user(user)
                .build();
        netherlandDwarfRabbit.setSterilised(true);
        treatmentHistories.forEach(treatmentHistory -> netherlandDwarfRabbit.addTreatmentHistory(treatmentHistory));
        return initAnimal(netherlandDwarfRabbit);
    }

    public Animal initFerret(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal ferret = Animal.builder()
                .name("Beacon")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("albino; blind but brave, very friendly; hormonal implant since 21 april 2020")
                .birthDate(getDateBackward(3))
                .sex("male")
                .shelterJoinDate(getDateBackward(0))
                .user(user)
                .build();
        ferret.setSterilised(false);
        treatmentHistories.forEach(treatmentHistory -> ferret.addTreatmentHistory(treatmentHistory));
        return initAnimal(ferret);
    }

    public Animal initCockatiel(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal cockatiel = Animal.builder()
                .name("Nico")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("white; very vocal, friendly, spent his life free-roaming around the house, feels bad in a cage")
                .birthDate(getDateBackward(10))
                .sex("male")
                .shelterJoinDate(getDateBackward(1))
                .user(user)
                .build();
        cockatiel.setSterilised(false);
        treatmentHistories.forEach(treatmentHistory -> cockatiel.addTreatmentHistory(treatmentHistory));
        return initAnimal(cockatiel);
    }

    public Animal initRussianTortoise(Species species, Race race, Set<Status> statuses, User user, Set<TreatmentHistory> treatmentHistories) {
        Animal russianTortoise = Animal.builder()
                .name("Thumper")
                .species(species)
                .race(race)
                .statuses(statuses)
                .description("three-legged, has a wheel in place of missing right hind leg, requires special terrarium adjustments")
                .birthDate(getDateBackward(14))
                .sex("female")
                .shelterJoinDate(getDateBackward(1))
                .user(user)
                .build();
        russianTortoise.setSterilised(false);
        treatmentHistories.forEach(treatmentHistory -> russianTortoise.addTreatmentHistory(treatmentHistory));
        return initAnimal(russianTortoise);
    }

    private Date getDateBackward(int years) {
        return new DateTime().minusYears(years).toDate();
    }

    @Transactional
    private Animal initAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

}
