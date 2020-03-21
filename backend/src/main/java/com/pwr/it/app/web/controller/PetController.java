package com.pwr.it.app.web.controller;

import com.pwr.it.app.data.domain.Pet;
import com.pwr.it.app.data.repository.PetRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Validated
@Controller("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetRepository petRepository;

    @Get("/all")
    @Operation(summary = "Get pets")
    public Iterable<Pet> test() {
        return petRepository.findAll();
    }

}
