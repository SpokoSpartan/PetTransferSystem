package com.pwr.it.app.web.controller;

import com.pwr.it.app.data.domain.User;
import com.pwr.it.app.data.domain.dto.response.AnimalDetailsResponse;
import com.pwr.it.app.data.domain.dto.response.AnimalResponse;
import com.pwr.it.app.services.AnimalService;
import com.pwr.it.app.services.UserService;
import com.pwr.it.app.web.exception.AnimalNotFoundException;
import io.micronaut.data.model.Page;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Animal")
@Controller("/api/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final UserService userService;

    @Operation(summary = "Get page of animals that are prepared for adoption (status -> READY_FOR_ADOPTION)")
    @Get("/all")
    public Page<AnimalResponse> getReadyAnimals(@QueryValue int page, @QueryValue int size) {
        return animalService.getPageOfReadyAnimals(page, size);
    }

    @Get("/my/all")
    public Page<AnimalResponse> getMyAnimals(@QueryValue int page, @QueryValue int size) {
        User loggedUser = userService.getLoggedUser();
        return animalService.getAnimalsOwnedBy(loggedUser, page, size);
    }

    @Operation(summary = "Get animal with given id")
    @Get("/one/{id}")
    public AnimalDetailsResponse getAnimalDetails(@PathVariable long id) throws AnimalNotFoundException {
        return animalService.getAnimalDetailsById(id);
    }

}
