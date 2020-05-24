package com.pwr.it.app.web.controller;

import com.pwr.it.app.data.domain.dto.request.TreatmentRequest;
import com.pwr.it.app.services.TreatmentHistoryService;
import com.pwr.it.app.web.exception.AnimalNotFoundException;
import com.pwr.it.app.web.exception.DateValidationException;
import com.pwr.it.app.web.exception.TreatmentNotFoundException;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@Tag(name = "Treatment")
@Controller("/api/treatment")
@RequiredArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED)
public class TreatmentController {

    private final TreatmentHistoryService treatmentHistoryService;

    @Operation(
            summary = "Add treatment history",
            description = "Add treatment history to animal with given id. " +
                    "Title is required. Start date mut be before end date"
    )
    @Post("/animal/{id}/add")
    public void addTreatmentToAnimal(@PathVariable long id, @Valid @Body TreatmentRequest treatmentRequest) throws AnimalNotFoundException, DateValidationException {
        treatmentHistoryService.addTreatmentToAnimal(treatmentRequest, id);
    }

    @Operation(
            summary = "Update treatment history",
            description = "Update treatment with given id." +
                    "Title is required. Start date mut be before end date." +
                    "If treatment status is canceled then cannot be updated."
    )
    @Post("/update/{id}")
    public void updateTreatment(@PathVariable long id, @Valid @Body TreatmentRequest treatmentRequest) throws DateValidationException, TreatmentNotFoundException {
        treatmentHistoryService.updateTreatment(treatmentRequest, id);
    }

    @Operation(
            summary = "Update treatment history",
            description = "Update treatment with given id." +
                    "Title is required. Start date mut be before end date." +
                    "If treatment status is canceled then cannot be updated."
    )
    @Post("/cancel/{id}")
    public void removeTreatment(@PathVariable long id) throws TreatmentNotFoundException {
        treatmentHistoryService.removeTreatment(id);
    }

}
