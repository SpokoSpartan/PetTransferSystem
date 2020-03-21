package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.Organization;
import com.pwr.it.app.data.domain.Race;
import com.pwr.it.app.data.domain.Species;
import com.pwr.it.app.data.domain.Status;
import com.pwr.it.app.data.domain.User;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;

import javax.inject.Singleton;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static com.pwr.it.app.data.domain.AnimalStatus.ADOPTED;
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

		Organization simpleOrganization = organizationSeeder.initSimpleAOrganization();

		User agnieszka = userSeeder.initAgnieszka(Optional.of(simpleOrganization));
		User jacek = userSeeder.initJacek(Optional.of(simpleOrganization));
		User jani = userSeeder.initJani(Optional.of(simpleOrganization));
		User piotr = userSeeder.initPiotr(Optional.empty());
		User wojtek = userSeeder.initWojtek(Optional.empty());
		User kacper = userSeeder.initKacper(Optional.empty());


		Set<Status> adopted = statusSeeder.initAnimalStatuses(dateBackward(128), NEW_IN_SHELTER, READY_FOR_ADOPTION, ADOPTED);
		//Animal animal = animalSeeder.initAnimal("Chester", cat, persianCat, null, "Descr", dateBackwardYears(2),)

	}

	private Date dateBackward(int days) {
		return DateTime.now().minusDays(days).toDate();
	}

}
