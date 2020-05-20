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

    public Race initVeiledChameleon() {
        Race veiledChameleon = new Race("Veiled chameleon");
        return initRace(veiledChameleon);
    }

    public Race initLeopardGecko() {
        Race veiledChameleon = new Race("Leopard gecko");
        return initRace(veiledChameleon);
    }

    public Race initHollandLopRabbit() {
        Race hollandLopRabbit = new Race("Holland lop rabbit");
        return initRace(hollandLopRabbit);
    }

    public Race initSulphurCrestedCockatoo() {
        Race sulphurCrestedCockatoo = new Race("Sulphur crested cockatoo ");
        return initRace(sulphurCrestedCockatoo);
    }

    public Race initSugarGlider() {
        Race sugarGlider = new Race("Sugar glider");
        return initRace(sugarGlider);
    }

    public Race initLionheadRabbit() {
        Race lionheadRabbit = new Race("Lionhead rabbit");
        return initRace(lionheadRabbit);
    }

    public Race initNetherlandDwarfRabbit() {
        Race netherlandDwarfRabbit = new Race("Netherland dwarf rabbit");
        return initRace(netherlandDwarfRabbit);
    }

    public Race initFerret() {
        Race ferret = new Race("Ferret");
        return initRace(ferret);
    }

    public Race initCockatiel() {
        Race cockatiel = new Race("Cockatiel");
        return initRace(cockatiel);
    }

    public Race initRussianTortoise() {
        Race russianTortoise = new Race("Russian tortoise");
        return initRace(russianTortoise);
    }

    @Transactional
    private Race initRace(Race race) {
        return raceRepository.save(race);
    }

}
