package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.Animal;
import com.pwr.it.app.data.domain.CountObjects;
import com.pwr.it.app.data.domain.User;
import com.pwr.it.app.data.domain.dto.response.AnimalDetailsResponse;
import com.pwr.it.app.data.domain.dto.response.AnimalResponse;
import com.pwr.it.app.data.repository.AnimalRepository;
import com.pwr.it.app.web.exception.AnimalNotFoundException;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

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
                .collect(Collectors.toList());
    }

    @Transactional
    public AnimalDetailsResponse getAnimalDetailsById(long id) throws AnimalNotFoundException {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new AnimalNotFoundException());
        return animal.translateToAnimalDetailsResponse();
    }

}
