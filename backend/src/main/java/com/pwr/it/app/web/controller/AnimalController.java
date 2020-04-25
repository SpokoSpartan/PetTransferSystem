package com.pwr.it.app.web.controller;

import com.pwr.it.app.data.domain.User;
import com.pwr.it.app.data.domain.dto.request.AnimalRequest;
import com.pwr.it.app.data.domain.dto.response.AnimalDetailsResponse;
import com.pwr.it.app.data.domain.dto.response.AnimalResponse;
import com.pwr.it.app.services.AnimalService;
import com.pwr.it.app.services.UserService;
import com.pwr.it.app.web.exception.AnimalNotFoundException;
import io.micronaut.data.model.Page;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

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

    @Operation(summary = "Get page of animals owned by logged user. Aty this moment random user is set as logged.")
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

    @Operation(
            summary = "Add animal",
            description = "Allows to add animal to shelter. Additional validations are used:\n" +
                    "| Name         |   Validation  |\n" +
                    "|--------------|---------------|\n" +
                    "|name          | max size 255  |\n" +
                    "|species(*)    | max size 255  |\n" +
                    "|race          | max size 255  |\n" +
                    "|description   | max size 1000 |\n" +
                    "|birthDate     | past          |\n" +
                    "|sex           | max size 255  |\n" +
                    "|imageUrl      | max size 500  |"
    )
    @Post("/create")
    public AnimalDetailsResponse createAnimal(@Body @Valid AnimalRequest animalRequest) {
        return animalService.createAnimal(animalRequest);
    }

    @Operation(
            summary = "Update animal with given id",
            description = "Additional validations are used:\n" +
                    "| Name         |   Validation  |\n" +
                    "|--------------|---------------|\n" +
                    "|name          | max size 255  |\n" +
                    "|species(*)    | max size 255  |\n" +
                    "|race          | max size 255  |\n" +
                    "|description   | max size 1000 |\n" +
                    "|birthDate     | past          |\n" +
                    "|sex           | max size 255  |\n" +
                    "|imageUrl      | max size 500  |"
    )
    @Post("/update/{id}")
    public AnimalDetailsResponse updateAnimal(@PathVariable long id, @Body @Valid AnimalRequest animalRequest) throws AnimalNotFoundException {
        return animalService.updateAnimal(id, animalRequest);
    }

}
