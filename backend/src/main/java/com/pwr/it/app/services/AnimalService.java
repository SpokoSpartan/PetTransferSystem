package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.*;
import com.pwr.it.app.data.domain.dto.request.AnimalRequest;
import com.pwr.it.app.data.domain.dto.response.AnimalDetailsResponse;
import com.pwr.it.app.data.domain.dto.response.AnimalResponse;
import com.pwr.it.app.data.repository.AnimalRepository;
import com.pwr.it.app.web.exception.AnimalNotFoundException;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final SpeciesService speciesService;
    private final RaceService raceService;
    private final StatusService statusService;
    private final UserService userService;

    @Transactional
    public Page<AnimalResponse> getPageOfReadyAnimals(int page, int size) {
        CountObjects countReadyAnimals = animalRepository.countAnimalForAdoption();

        Pageable pageable = Pageable.from(page, size);
        List<AnimalResponse> animals = translateToResponse(
                animalRepository.findAllReadyForAdoption(size, page * size));

        return Page.of(animals, pageable, countReadyAnimals.getCount());
    }

    @Transactional
    public Page<AnimalResponse> getAnimalsOwnedBy(User loggedUser, int page, int size) {
        CountObjects countOwnedAnimal = animalRepository.countAnimalsOwnedByUser(loggedUser.getId());

        Pageable pageable = Pageable.from(page, size);
        List<AnimalResponse> animals = translateToResponse(
                animalRepository.findAllOwnedByUser(loggedUser.getId(), size, page * size));

        return Page.of(animals, pageable, countOwnedAnimal.getCount());
    }

    private List<AnimalResponse> translateToResponse(Iterable<Animal> animals) {
        return StreamSupport.stream(
                animals.spliterator(), false)
                .map(Animal::translateToAnimalResponse)
                .sorted()
                .collect(Collectors.toList());
    }

    @Transactional
    public AnimalDetailsResponse getAnimalDetailsById(long id) throws AnimalNotFoundException {
        Animal animal = getAnimalById(id);
        return animal.translateToAnimalDetailsResponse();
    }

    @Transactional
    public AnimalDetailsResponse createAnimal(AnimalRequest animalRequest) {
        Animal animal = Animal.builder()
                .name(animalRequest.getName())
                .species(findSpecies(animalRequest.getSpecies()))
                .race(findRace(animalRequest.getRace()))
                .imageUrl(animalRequest.getImageUrl())
                .statuses(addNewInShelterStatus())
                .description(animalRequest.getDescription())
                .birthDate(animalRequest.getBirthDate())
                .sex(animalRequest.getSex())
                .shelterJoinDate(new Date())
                .user(findOwner())
                .build();
        animal.setSterilised(animalRequest.getSterilised());
        return animalRepository.save(animal).translateToAnimalDetailsResponse();
    }

    private Set<Status> addNewInShelterStatus() {
        return Collections.singleton(
                statusService.addNewStatus(AnimalStatus.NEW_IN_SHELTER, new Date()));
    }

    private User findOwner() {
        return userService.getLoggedUser();
    }

    @Transactional
    public AnimalDetailsResponse updateAnimal(long id, AnimalRequest animalRequest) throws AnimalNotFoundException {
        Animal animal = getAnimalById(id);
        animal.setName(animalRequest.getName());
        animal.setSpecies(findSpecies(animalRequest.getSpecies()));
        animal.setRace(findRace(animalRequest.getRace()));
        animal.setImageUrl(animalRequest.getImageUrl());
        animal.setDescription(animalRequest.getDescription());
        animal.setBirthDate(animalRequest.getBirthDate());
        animal.setSex(animalRequest.getSex());
        return animal.translateToAnimalDetailsResponse();
    }

    @Transactional
    public void archiveAnimal(Long id) throws AnimalNotFoundException {
        Animal animal = getAnimalById(id);
        animal.setArchived(true);
    }

    @Transactional
    public void reverseAnimalArchiving(Long id) throws AnimalNotFoundException {
        Animal animal = getAnimalById(id);
        animal.setArchived(false);
    }

    @Transactional
    public void removeAnimal(Long id) throws AnimalNotFoundException {
        Animal animal = getAnimalById(id);
        animalRepository.delete(animal);
    }

    public Animal getAnimalById(long id) throws AnimalNotFoundException {
        return animalRepository.findById(id).orElseThrow(() -> new AnimalNotFoundException());
    }

    private Species findSpecies(String species) {
        if (species == null) {
            return null;
        }
        return speciesService.getSavedSpeciesByName(species);
    }

    private Race findRace(String race) {
        if (race == null) {
            return null;
        }
        return raceService.getSavedRaceByName(race);
    }

}

