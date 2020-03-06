package com.pwr.it.app.web.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Validated
@Controller("/pets")
@RequiredArgsConstructor
public class PetController {

    @Get("/test")
    public String testMethod(@NotBlank String petName) {
        return "Server working correctly. Pet name is: " + petName + ".";
    }

}
