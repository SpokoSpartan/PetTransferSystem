package com.pwr.it.app.web.controller;

import com.pwr.it.app.services.SpeciesService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "Species")
@Controller("/api/species")
@RequiredArgsConstructor
public class SpeciesController {

    private final SpeciesService speciesService;

    @Operation(
            summary = "Get all animal species",
            description = "Species from this endpoint may be used during animal process creation"
    )
    @Get("/all")
    public List<String> getAllSpecies() {
        return speciesService.getAllSpecies();
    }

}
