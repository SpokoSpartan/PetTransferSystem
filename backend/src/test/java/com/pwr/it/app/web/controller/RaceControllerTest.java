package com.pwr.it.app.web.controller;

import com.jayway.jsonpath.JsonPath;
import com.pwr.it.app.services.RaceService;
import io.micronaut.context.annotation.Property;
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
@Property(name = "micronaut.server.port", value = "-1")
@Property(name = "micronaut.security.enabled", value = "false")
public class RaceControllerTest {

    private static final List<String> RACE_LIST = Arrays.asList("Bulldog", "Persian cat", "Royal python");

    @Inject
    @Client("/")
    RxHttpClient client;

    @Inject
    RaceService raceService;

    @Test
    @DisplayName("When user requests races then list should return")
    void shouldReturnListOfRaces() {
        when(raceService.getAllRaces()).thenReturn(RACE_LIST);

        String response = client.toBlocking().retrieve(HttpRequest.GET("/api/race/all"), String.class);

        assertAll(
                () -> assertEquals(RACE_LIST.get(0), JsonPath.read(response, "[0]")),
                () -> assertEquals(RACE_LIST.get(1), JsonPath.read(response, "[1]")),
                () -> assertEquals(RACE_LIST.get(2), JsonPath.read(response, "[2]"))
        );
    }

    @MockBean(RaceService.class)
    RaceService raceService() {
        return mock(RaceService.class);
    }

}
