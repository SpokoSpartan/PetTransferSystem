package com.pwr.it.app.web.controller;

import com.pwr.it.app.data.domain.dto.response.AnimalDetailsResponse;
import com.pwr.it.app.data.domain.dto.response.AnimalResponse;
import com.pwr.it.app.services.AnimalService;
import com.pwr.it.app.web.exception.AnimalNotFoundException;
import io.micronaut.data.model.Page;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Controller("/api/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @Operation(summary = "Get page of animals")
    @Get("/all")
    public Page<AnimalResponse> getAnimals(@QueryValue int page, @QueryValue int size) {
        return animalService.getPageOfAnimals(page, size);
    }

    @Operation(summary = "Get animal with given id")
    @Get("/one/{id}")
    public AnimalDetailsResponse getAnimalDetails(@PathVariable long id) throws AnimalNotFoundException {
        return animalService.getAnimalDetailsById(id);
    }

}
