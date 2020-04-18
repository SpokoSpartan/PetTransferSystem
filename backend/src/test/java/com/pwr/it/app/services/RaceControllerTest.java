package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.Race;
import com.pwr.it.app.data.repository.RaceRepository;
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
public class RaceControllerTest {

    private static final Race PERSIAN_CAT = new Race("Persian cat");
    private static final Race BULLDOG = new Race("Bulldog");
    private static final Race ROYAL_PYTHON = new Race("Royal python");
    private static final Iterable<Race> ITERABLE_RACE = Arrays.asList(PERSIAN_CAT, BULLDOG, ROYAL_PYTHON);

    @Inject
    RaceRepository raceRepository;

    @Inject
    RaceService raceService;


    @Test
    @DisplayName("When translating races then result should be sorted alphabetically")
    void shouldReturnSortedListOfRaces() {
        when(raceRepository.findAll()).thenReturn(ITERABLE_RACE);

        List<String> result = raceService.getAllRaces();

        assertAll(
                () -> assertEquals(BULLDOG.getName(), result.get(0)),
                () -> assertEquals(PERSIAN_CAT.getName(), result.get(1)),
                () -> assertEquals(ROYAL_PYTHON.getName(), result.get(2))
        );
    }


    @MockBean(RaceRepository.class)
    RaceRepository raceRepository() {
        return mock(RaceRepository.class);
    }

}
