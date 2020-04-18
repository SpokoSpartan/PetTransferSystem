package com.pwr.it.app.web.controller;

import com.pwr.it.app.services.RaceService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller("/api/race")
@RequiredArgsConstructor
public class RaceController {

    private final RaceService raceService;

    @Operation(
            summary = "Get all animals races",
            description = "Races from this endpoint may be used during animal process creation"
    )
    @Get("/all")
    public List<String> getAllRaces() {
        return raceService.getAllRaces();
    }

}
