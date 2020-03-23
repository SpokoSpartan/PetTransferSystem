package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.AnimalStatus;
import com.pwr.it.app.data.domain.Organization;
import com.pwr.it.app.data.domain.Race;
import com.pwr.it.app.data.domain.Species;
import com.pwr.it.app.data.domain.Status;
import com.pwr.it.app.data.domain.TreatmentHistory;
import com.pwr.it.app.data.domain.User;
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

import static com.pwr.it.app.data.domain.AnimalStatus.ADOPTED;
import static com.pwr.it.app.data.domain.AnimalStatus.DIED;
import static com.pwr.it.app.data.domain.AnimalStatus.NEW_IN_SHELTER;
import static com.pwr.it.app.data.domain.AnimalStatus.READY_FOR_ADOPTION;

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

        Species cat = speciesSeeder.initCat();

        User agnieszka = userSeeder.initAgnieszka(Optional.of(simpleOrganization));
        Race persianCat = raceSeeder.initPersianCat();
        Set<Status> persianCatStatuses = statusSeeder.initAnimalStatuses(dateBackward(365), NEW_IN_SHELTER, READY_FOR_ADOPTION, ADOPTED);
        Status persianCatStatus = getStatusByName(persianCatStatuses, NEW_IN_SHELTER);
        Set<TreatmentHistory> persianCatTreatmentHistory = treatmentHistorySeeder.initPreventiveExamination(
                persianCatStatus.getStatusBeginning(), persianCatStatus.getStatusEnd());
        animalSeeder.initPersianCat(cat, persianCat, persianCatStatuses, agnieszka, persianCatTreatmentHistory);

        User jacek = userSeeder.initJacek(Optional.of(simpleOrganization));
        Race siameseCat = raceSeeder.initSiameseCat();
        Set<Status> siameseCatStatuses = statusSeeder.initAnimalStatuses(dateBackward(730), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        animalSeeder.initSiameseCat(cat, siameseCat, siameseCatStatuses, jacek, Collections.emptySet());


        Species dog = speciesSeeder.initDog();

        User jani = userSeeder.initJani(Optional.of(simpleOrganization));
        Race husky = raceSeeder.initSiberianHusky();
        Set<Status> huskyDogStatuses = statusSeeder.initAnimalStatuses(dateBackward(730), NEW_IN_SHELTER);
        Status huskyDogStatus = getStatusByName(huskyDogStatuses, NEW_IN_SHELTER);
        Set<TreatmentHistory> huskyDogTreatmentHistory = treatmentHistorySeeder.initChipImplantation(
                huskyDogStatus.getStatusBeginning(), huskyDogStatus.getStatusEnd());
        animalSeeder.initHuskyDog(dog, husky, huskyDogStatuses, jani, huskyDogTreatmentHistory);

        User piotr = userSeeder.initPiotr(Optional.empty());
        Race bulldog = raceSeeder.initBulldog();
        Set<Status> bulldogStatuses = statusSeeder.initAnimalStatuses(dateBackward(365), NEW_IN_SHELTER, READY_FOR_ADOPTION);
        Status bulldogStatus = getStatusByName(bulldogStatuses, READY_FOR_ADOPTION);
        Set<TreatmentHistory> bulldogTreatmentHistory = treatmentHistorySeeder.initChipImplantation(
                bulldogStatus.getStatusBeginning(), bulldogStatus.getStatusEnd());
        animalSeeder.initBulldog(dog, bulldog, bulldogStatuses, piotr, bulldogTreatmentHistory);


        Species snake = speciesSeeder.initSnake();

        User wojtek = userSeeder.initWojtek(Optional.empty());
        Race royalPython = raceSeeder.initRoyalPython();
        Set<Status> royalPythonStatuses = statusSeeder.initAnimalStatuses(dateBackward(365), READY_FOR_ADOPTION, DIED);
        animalSeeder.initRoyalPython(snake, royalPython, royalPythonStatuses, wojtek, Collections.emptySet());


        userSeeder.initKacper(Optional.empty());
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
