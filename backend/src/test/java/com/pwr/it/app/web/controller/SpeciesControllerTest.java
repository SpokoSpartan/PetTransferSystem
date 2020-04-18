package com.pwr.it.app.web.controller;

import com.jayway.jsonpath.JsonPath;
import com.pwr.it.app.services.SpeciesService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
public class SpeciesControllerTest {

    private static final List<String> SPECIES_LIST = Arrays.asList("cat", "dog", "snake");

    @Inject
    @Client("/")
    RxHttpClient client;

    @Inject
    SpeciesService speciesService;

    @Test
    @DisplayName("When user requests species then list should return")
    void shouldReturnListOfSpecies() {
        when(speciesService.getAllSpecies()).thenReturn(SPECIES_LIST);

        String response = client.toBlocking().retrieve(HttpRequest.GET("/api/species/all"), String.class);

        assertAll(
                () -> assertEquals(SPECIES_LIST.get(0), JsonPath.read(response, "[0]")),
                () -> assertEquals(SPECIES_LIST.get(1), JsonPath.read(response, "[1]")),
                () -> assertEquals(SPECIES_LIST.get(2), JsonPath.read(response, "[2]"))
        );
    }

    @MockBean(SpeciesService.class)
    SpeciesService speciesService() {
        return mock(SpeciesService.class);
    }

}
