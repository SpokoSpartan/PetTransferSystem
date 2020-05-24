package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.*;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static com.pwr.it.app.data.domain.AnimalStatus.*;

@Singleton
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationEventListener<ServerStartupEvent> {

    private final RaceSeeder raceSeeder;
    private final SpeciesSeeder speciesSeeder;
    private final StatusSeeder statusSeeder;
    private final OrganizationSeeder organizationSeeder;
    private final UserSeeder userSeeder;
    private final TreatmentHistorySeeder treatmentHistorySeeder;
    private final AnimalSeeder animalSeeder;

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        Organization simpleOrganization = organizationSeeder.initSimpleAOrganization();

        User agnieszka = userSeeder.initAgnieszka(Optional.of(simpleOrganization));
        User jacek = userSeeder.initJacek(Optional.of(simpleOrganization));
        User jani = userSeeder.initJani(Optional.of(simpleOrganization));

        User piotr = userSeeder.initPiotr(Optional.empty());
        User kacper = userSeeder.initKacper(Optional.empty());
        User wojtek = userSeeder.initWojtek(Optional.empty());


        Species cat = speciesSeeder.initCat();
        Race persianCat = raceSeeder.initPersianCat();
        Set<Status> persianCatStatuses = statusSeeder.initAnimalStatuses(dateBackward(365), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        Status persianCatStatus = getStatusByName(persianCatStatuses, NEW_IN_SHELTER);
        Set<TreatmentHistory> persianCatTreatmentHistory = treatmentHistorySeeder.initPreventiveExamination(
                persianCatStatus.getStatusBeginning(), persianCatStatus.getStatusEnd());
        animalSeeder.initPersianCat(cat, persianCat, persianCatStatuses, agnieszka, persianCatTreatmentHistory);

        Race siameseCat = raceSeeder.initSiameseCat();
        Set<Status> siameseCatStatuses = statusSeeder.initAnimalStatuses(dateBackward(730), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        animalSeeder.initSiameseCat(cat, siameseCat, siameseCatStatuses, jacek, Collections.emptySet());


        Species dog = speciesSeeder.initDog();
        Race husky = raceSeeder.initSiberianHusky();
        Set<Status> huskyDogStatuses = statusSeeder.initAnimalStatuses(dateBackward(730), NEW_IN_SHELTER);
        Status huskyDogStatus = getStatusByName(huskyDogStatuses, NEW_IN_SHELTER);
        Set<TreatmentHistory> huskyDogTreatmentHistory = treatmentHistorySeeder.initChipImplantation(
                huskyDogStatus.getStatusBeginning(), huskyDogStatus.getStatusEnd());
        animalSeeder.initHuskyDog(dog, husky, huskyDogStatuses, jani, huskyDogTreatmentHistory);

        Race bulldog = raceSeeder.initBulldog();
        Set<Status> bulldogStatuses = statusSeeder.initAnimalStatuses(dateBackward(365), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        Status bulldogStatus = getStatusByName(bulldogStatuses, READY_FOR_ADOPTION);
        Set<TreatmentHistory> bulldogTreatmentHistory = treatmentHistorySeeder.initChipImplantation(
                bulldogStatus.getStatusBeginning(), bulldogStatus.getStatusEnd());
        animalSeeder.initBulldog(dog, bulldog, bulldogStatuses, piotr, bulldogTreatmentHistory);


        Species snake = speciesSeeder.initSnake();
        Race royalPython = raceSeeder.initRoyalPython();
        Set<Status> royalPythonStatuses = statusSeeder.initAnimalStatuses(dateBackward(365), READY_FOR_ADOPTION, DIED);
        animalSeeder.initRoyalPython(snake, royalPython, royalPythonStatuses, wojtek, Collections.emptySet());


        Species chameleon = speciesSeeder.initChameleon();
        Race veiledChameleon = raceSeeder.initVeiledChameleon();
        Set<Status> veiledChameleonStatuses = statusSeeder.initAnimalStatuses(dateBackward(128), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        animalSeeder.initVeiledChameleon(chameleon, veiledChameleon, veiledChameleonStatuses, kacper, Collections.emptySet());


        Species eublepharis = speciesSeeder.initEublepharis();
        Race leopardGecko = raceSeeder.initLeopardGecko();
        Set<Status> leopardGeckoStatuses = statusSeeder.initAnimalStatuses(dateBackward(365), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        animalSeeder.initLeopardGecko(eublepharis, leopardGecko, leopardGeckoStatuses, kacper, Collections.emptySet());


        Species rabbit = speciesSeeder.initRabbit();
        Race hollandLopRabbit = raceSeeder.initHollandLopRabbit();
        Set<Status> hollandLopRabbitStatuses = statusSeeder.initAnimalStatuses(dateBackward(365), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        animalSeeder.initHollandLopRabbit(rabbit, hollandLopRabbit, hollandLopRabbitStatuses, wojtek, Collections.emptySet());

        Race lionheadRabbit = raceSeeder.initLionheadRabbit();
        Set<Status> lionheadRabbitStatuses = statusSeeder.initAnimalStatuses(dateBackward(700), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        animalSeeder.initLionheadRabbit(rabbit, lionheadRabbit, lionheadRabbitStatuses, agnieszka, Collections.emptySet());

        Race netherlandDwarfRabbit = raceSeeder.initNetherlandDwarfRabbit();
        Set<Status> netherlandDwarfRabbitStatuses = statusSeeder.initAnimalStatuses(dateBackward(365), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        animalSeeder.initNetherlandDwarfRabbit(rabbit, netherlandDwarfRabbit, netherlandDwarfRabbitStatuses, jani, Collections.emptySet());


        Species cacatua = speciesSeeder.initCacatua();
        Race sulphurCrestedCockatoo = raceSeeder.initSulphurCrestedCockatoo();
        Set<Status> sulphurCrestedCockatooStatuses = statusSeeder.initAnimalStatuses(dateBackward(360), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        animalSeeder.initSulphurCrestedCockatoo(cacatua, sulphurCrestedCockatoo, sulphurCrestedCockatooStatuses, wojtek, Collections.emptySet());


        Species sugarGliderSp = speciesSeeder.initSugarGlider();
        Race sugarGliderRc = raceSeeder.initSugarGlider();
        Set<Status> sugarGliderStatusesFirst = statusSeeder.initAnimalStatuses(dateBackward(4), NEW_IN_SHELTER);
        animalSeeder.initFirstSugarGlider(sugarGliderSp, sugarGliderRc, sugarGliderStatusesFirst, piotr, Collections.emptySet());

        Set<Status> sugarGliderStatusesSecond = statusSeeder.initAnimalStatuses(dateBackward(4), NEW_IN_SHELTER);
        animalSeeder.initSecondSugarGlider(sugarGliderSp, sugarGliderRc, sugarGliderStatusesSecond, piotr, Collections.emptySet());


        Species weasel = speciesSeeder.initWeasel();
        Race ferret = raceSeeder.initFerret();
        Set<Status> ferretStatuses = statusSeeder.initAnimalStatuses(dateBackward(3), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        animalSeeder.initFerret(weasel, ferret, ferretStatuses, jacek, Collections.emptySet());


        Race cockatiel = raceSeeder.initCockatiel();
        Set<Status> cockatielStatuses = statusSeeder.initAnimalStatuses(dateBackward(1095), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        animalSeeder.initCockatiel(null, cockatiel, cockatielStatuses, wojtek, Collections.emptySet());


        Species tortoise = speciesSeeder.initTortoise();
        Race russianTortoise = raceSeeder.initRussianTortoise();
        Set<Status> russianTortoiseStatuses = statusSeeder.initAnimalStatuses(dateBackward(365), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        animalSeeder.initRussianTortoise(tortoise, russianTortoise, russianTortoiseStatuses, kacper, Collections.emptySet());
    }

    private Date dateBackward(int days) {
        return DateTime.now().minusDays(days).toDate();
    }

    @Transactional
    private Status getStatusByName(Set<Status> statuses, AnimalStatus animalStatus) {
        return statuses.stream()
                .filter(status -> status.getAnimalStatus().equals(animalStatus))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Status with name " + animalStatus + " not found"));
    }

}
