package com.pwr.it.app.web.controller;


import com.jayway.jsonpath.JsonPath;
import com.pwr.it.app.data.domain.dto.response.AnimalDetailsResponse;
import com.pwr.it.app.services.AnimalService;
import com.pwr.it.app.web.exception.AnimalNotFoundException;
import io.micronaut.data.model.Page;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
public class AnimalControllerTest {

    private static final Long ANIMAL_ID = 1L;
    private static final String ANIMAL_NAME = "Pimpek";

    @Inject
    @Client("/")
    RxHttpClient client;

    @Inject
    AnimalService animalService;


    @Test
    @DisplayName("When user requests list of animals then Page object should be return")
    void shouldReturnPageableObject() {
        when(animalService.getPageOfAnimals(anyInt(), anyInt())).thenReturn(Page.empty());

        String response = client.toBlocking().retrieve(HttpRequest.GET("/api/animal/all?page=0&size=2"), String.class);

        assertAll(
                () -> assertEquals(new Integer(0), JsonPath.read(response, "pageNumber")),
                () -> assertEquals(new Integer(0), JsonPath.read(response, "numberOfElements")),
                () -> assertEquals(new Integer(0), JsonPath.read(response, "size"))
        );
    }

    @Test
    @DisplayName("When user requests animal details then data should be return")
    void shouldReturnAnimalDetails() throws AnimalNotFoundException {
        when(animalService.getAnimalDetailsById(ANIMAL_ID)).thenReturn(prepareResponse());

        String response = client.toBlocking().retrieve(HttpRequest.GET("/api/animal/one/" + ANIMAL_ID), String.class);

        assertAll(
                () -> assertEquals(new Integer(ANIMAL_ID.intValue()), JsonPath.read(response, "id")),
                () -> assertEquals(ANIMAL_NAME, JsonPath.read(response, "name"))
        );
    }

    @Test
    @DisplayName("When user requests animal with given id and not found then should return error")
    void shouldReturnErrorWhenAnimalNotFound() throws AnimalNotFoundException {
        when(animalService.getAnimalDetailsById(ANIMAL_ID)).thenThrow(new AnimalNotFoundException());

        try {
            client.toBlocking().retrieve(HttpRequest.GET("/api/animal/one/" + ANIMAL_ID), String.class);
        } catch (HttpClientResponseException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getResponse().getStatus());
            return;
        }

        throw new IllegalStateException("HttpClientResponseException exception should be thrown");
    }

    private AnimalDetailsResponse prepareResponse() {
        return AnimalDetailsResponse.builder()
                .id(ANIMAL_ID)
                .name(ANIMAL_NAME)
                .build();
    }

    @MockBean(AnimalService.class)
    AnimalService animalService() {
        return mock(AnimalService.class);
    }

}
