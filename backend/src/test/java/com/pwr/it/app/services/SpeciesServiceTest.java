package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.Species;
import com.pwr.it.app.data.repository.SpeciesRepository;
import io.micronaut.context.annotation.Property;
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
public class SpeciesServiceTest {

    private static final Species DOG = new Species("cat");
    private static final Species CAT = new Species("cat");
    private static final Iterable<Species> ITERABLE_SPECIES = Arrays.asList(DOG, CAT);

    @Inject
    SpeciesRepository speciesRepository;

    @Inject
    SpeciesService speciesService;


    @Test
    @DisplayName("When translating species then result should be sorted alphabetically")
    void shouldReturnSortedListOfSpecies() {
        when(speciesRepository.findAll()).thenReturn(ITERABLE_SPECIES);

        List<String> result = speciesService.getAllSpecies();

        assertAll(
                () -> assertEquals(CAT.getName(), result.get(0)),
                () -> assertEquals(DOG.getName(), result.get(1))
        );
    }


    @MockBean(SpeciesRepository.class)
    SpeciesRepository speciesRepository() {
        return mock(SpeciesRepository.class);
    }

}
