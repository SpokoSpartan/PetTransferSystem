package com.pwr.it.app.web.controller;

import com.pwr.it.app.data.domain.AnimalStatus;
import com.pwr.it.app.data.domain.dto.response.StatusResponse;
import com.pwr.it.app.services.StatusService;
import com.pwr.it.app.web.exception.AnimalNotFoundException;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "Status")
@Controller("/api/status")
@RequiredArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED)
public class StatusController {

    private final StatusService statusService;

    @Operation(
            summary = "Change animal status",
            description = "Add new status to animal. End date in previous status and " +
                    "start date in new status will be set to actual date."
    )
    @Post("/animal/{id}/add")
    public void changeAnimalStatus(@PathVariable long id, @QueryValue AnimalStatus status) throws AnimalNotFoundException {
        statusService.changeAnimalStatus(id, status);
    }

    @Operation(
            summary = "Get animal statuses history",
            description = "Get all history of statuses with beginning date, end date and name."
    )
    @Get("animal/{id}/all")
    public List<StatusResponse> getAnimalStatusesHistory(@PathVariable long id) throws AnimalNotFoundException {
        return statusService.getAnimalStatusesHistory(id);
    }

}
