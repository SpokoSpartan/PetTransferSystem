package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.Animal;
import com.pwr.it.app.data.domain.AnimalStatus;
import com.pwr.it.app.data.domain.CountObjects;
import com.pwr.it.app.data.domain.Status;
import com.pwr.it.app.data.domain.User;
import com.pwr.it.app.data.domain.dto.response.AnimalLocationType;
import com.pwr.it.app.data.domain.dto.response.AnimalResponse;
import com.pwr.it.app.data.repository.AnimalRepository;
import io.micronaut.context.annotation.Property;
import io.micronaut.data.model.Page;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
@Property(name = "micronaut.server.port", value = "-1")
@Property(name = "micronaut.security.enabled", value = "false")
public class AnimalServiceTest {

    private static final String ANIMAL_NAME = "Pimpek";
    private static final AnimalStatus STATUS = AnimalStatus.NEW_IN_SHELTER;
    private static final Status ANIMAL_STATUS = new Status(STATUS, new Date(), null);
    private static final String USER_ADDRESS = "Wrocław, Poland";
    private static final String USER_NAME = "Wojtek";
    private static final String USER_PHONE = "+48 987 876 108";
    private static final String USER_EMAIL = "wojtek@wojtek.com";
    private static final User ANIMAL_USER = new User(USER_NAME, USER_PHONE, USER_EMAIL, USER_ADDRESS);

    @Inject
    AnimalRepository animalRepository;

    @Inject
    AnimalService animalService;

    @Test
    @DisplayName("When requesting page of available animals then should translate correctly")
    void shouldReturnTranslatedObjectWhenAskingForPreparedAnimals() {
        when(animalRepository.countAnimalForAdoption()).thenReturn(new CountObjects(1L));
        Animal animal = mockAnimal();
        when(animalRepository.findAllReadyForAdoption(anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(animal));

        Page<AnimalResponse> response = animalService.getPageOfReadyAnimals(0, 1);

        validateTranslatedData(response);
    }

    @Test
    @DisplayName("When requesting page of owned animals then should translate correctly")
    void shouldReturnTranslatedObjectWhenAskingForOwnedAnimals() {
        when(animalRepository.countAnimalsOwnedByUser(anyLong())).thenReturn(new CountObjects(1L));
        Animal animal = mockAnimal();
        when(animalRepository.findAllOwnedByUser(anyLong(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(animal));
        User loggedUser = mock(User.class);
        when(loggedUser.getId()).thenReturn(1L);

        Page<AnimalResponse> response = animalService.getAnimalsOwnedBy(loggedUser, 0, 1);

        validateTranslatedData(response);
    }

    private void validateTranslatedData(Page<AnimalResponse> response) {
        assertAll(
                () -> assertEquals(1L, response.getTotalSize()),
                () -> assertEquals(1L, response.getContent().size()),

                () -> assertEquals(ANIMAL_NAME, response.getContent().get(0).getName()),
                () -> assertEquals(USER_ADDRESS, response.getContent().get(0).getLocation()),
                () -> assertEquals(AnimalLocationType.USER, response.getContent().get(0).getLocationType()),
                () -> assertEquals(STATUS.toString(), response.getContent().get(0).getStatus())
        );
    }

    private Animal mockAnimal() {
        return Animal.builder()
                .name(ANIMAL_NAME)
                .statuses(Collections.singleton(ANIMAL_STATUS))
                .user(ANIMAL_USER)
                .build();
    }

    @MockBean(AnimalRepository.class)
    AnimalRepository animalRepository() {
        return mock(AnimalRepository.class);
    }

}
