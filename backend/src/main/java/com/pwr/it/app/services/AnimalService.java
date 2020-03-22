package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.Animal;
import com.pwr.it.app.data.domain.dto.response.AnimalDetailsResponse;
import com.pwr.it.app.data.domain.dto.response.AnimalResponse;
import com.pwr.it.app.data.repository.AnimalRepository;
import com.pwr.it.app.web.exception.AnimalNotFoundException;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

    @Transactional
    public Page<AnimalResponse> getPageOfAnimals(int page, int size) {
        Pageable pageable = Pageable.from(page, size);
        return animalRepository.findAll(pageable).map(animal -> animal.translateToAnimalResponse());
    }

    @Transactional
    public AnimalDetailsResponse getAnimalDetailsById(long id) throws AnimalNotFoundException {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new AnimalNotFoundException());
        return animal.translateToAnimalDetailsResponse();
    }

}
