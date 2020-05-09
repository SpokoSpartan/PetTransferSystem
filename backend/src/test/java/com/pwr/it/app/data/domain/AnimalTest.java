package com.pwr.it.app.data.domain;

import com.pwr.it.app.data.domain.dto.response.AnimalDetailsResponse;
import com.pwr.it.app.data.domain.dto.response.AnimalLocationType;
import com.pwr.it.app.data.domain.dto.response.AnimalResponse;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class AnimalTest {

    private static final Date BIRTH_DATE = new Date(12345678);
    private static final Date SHELTER_JOIN_DATE = new Date(12345680);
    private static final String NAME = "Pimpek";
    private static final Race RACE = new Race("dog");
    private static final Species SPECIES = new Species("Foxterier");
    private static final String SEX = "female";
    private static final String DESCRIPTION = "This is dog description.";
    private static final Boolean STERILISED = true;
    private static final String IMAGE_URL = "https://pbs.twimg.com/media/DOINwa5VQAUtkfh.jpg";

    private static final Date STATUS_START_DATE = new Date(22334455);
    private static final AnimalStatus STATUS = AnimalStatus.NEW_IN_SHELTER;

    private static final String USER_NAME = "Wojtek";
    private static final String USER_PHONE_NUMBER = "+48 123456789";
    private static final String USER_EMAIL = "wojtek@wojtek.wojtek";
    private static final String USER_ADDRESS = "Wrocław Topolna 37D";
    private static final User USER = new User(USER_NAME, USER_PHONE_NUMBER, USER_EMAIL, USER_ADDRESS);

    private static final String ORGANIZATION_NAME = "Wojtek";
    private static final String ORGANIZATION_PHONE_NUMBER = "+48 123456789";
    private static final String ORGANIZATION_EMAIL = "wojtek@wojtek.wojtek";
    private static final String ORGANIZATION_ADDRESS = "Wrocław Topolna 37D";
    private static final Organization ORGANIZATION = new Organization(USER_NAME, USER_PHONE_NUMBER, USER_EMAIL, USER_ADDRESS);

    @Test
    @DisplayName("Animal object should be correctly translated to AnimalResponse object")
    void shouldCorrectlyTranslateToAnimalResponse() {
        AnimalResponse response = prepareAnimalObject().translateToAnimalResponse();

        assertAll(
                () -> assertEquals(NAME, response.getName()),
                () -> assertEquals(IMAGE_URL, response.getImageUrl()),
                () -> assertEquals(STATUS.toString(), response.getStatus()),
                () -> assertEquals(USER_ADDRESS, response.getLocation()),
                () -> assertEquals(AnimalLocationType.USER, response.getLocationType())
        );
    }

    @Test
    @DisplayName("Animal object should be correctly translated to AnimalDetailsResponse object")
    void shouldCorrectlyTranslateToAnimalDetailsResponse() {
        AnimalDetailsResponse response = prepareAnimalObject().translateToAnimalDetailsResponse();

        assertAll(
                () -> assertEquals(NAME, response.getName()),
                () -> assertEquals(SPECIES.getName(), response.getSpecies()),
                () -> assertEquals(RACE.getName(), response.getRace()),
                () -> assertEquals(IMAGE_URL, response.getImageUrl()),
                () -> assertEquals(STATUS.toString(), response.getStatus()),
                () -> assertEquals(DESCRIPTION, response.getDescription()),
                () -> assertEquals(BIRTH_DATE, response.getBirthDate()),
                () -> assertEquals(SEX, response.getSex()),
                () -> assertEquals(STERILISED, response.getSterilized()),
                () -> assertEquals(SHELTER_JOIN_DATE, response.getShelterJoinDate()),
                () -> assertEquals(AnimalLocationType.USER, response.getAnimalLocation().getLocationType()),
                () -> assertEquals(USER_NAME, response.getAnimalLocation().getFullName()),
                () -> assertEquals(USER_PHONE_NUMBER, response.getAnimalLocation().getPhone()),
                () -> assertEquals(USER_EMAIL, response.getAnimalLocation().getEmail()),
                () -> assertEquals(USER_ADDRESS, response.getAnimalLocation().getAddress())
        );
    }

    @Test
    @DisplayName("If user belong to organization then animal address is taken from organization")
    void shouldSetOrganizationAddressIfExists() {
        Animal animal = prepareAnimalObject();
        animal.getUser().setOrganization(ORGANIZATION);
        AnimalDetailsResponse response = prepareAnimalObject().translateToAnimalDetailsResponse();
        animal.getUser().setOrganization(null);

        assertAll(
                () -> assertEquals(AnimalLocationType.ORGANIZATION, response.getAnimalLocation().getLocationType()),
                () -> assertEquals(ORGANIZATION_NAME, response.getAnimalLocation().getFullName()),
                () -> assertEquals(ORGANIZATION_PHONE_NUMBER, response.getAnimalLocation().getPhone()),
                () -> assertEquals(ORGANIZATION_EMAIL, response.getAnimalLocation().getEmail()),
                () -> assertEquals(ORGANIZATION_ADDRESS, response.getAnimalLocation().getAddress())
        );
    }

    @Test
    @DisplayName("If user not belong to organization then animal address is taken from user")
    void shouldSetUserAddressIfOrganizationNotExists() {
        AnimalDetailsResponse response = prepareAnimalObject().translateToAnimalDetailsResponse();

        assertAll(
                () -> assertEquals(AnimalLocationType.USER, response.getAnimalLocation().getLocationType()),
                () -> assertEquals(USER_NAME, response.getAnimalLocation().getFullName()),
                () -> assertEquals(USER_PHONE_NUMBER, response.getAnimalLocation().getPhone()),
                () -> assertEquals(USER_EMAIL, response.getAnimalLocation().getEmail()),
                () -> assertEquals(USER_ADDRESS, response.getAnimalLocation().getAddress())
        );
    }

    private Animal prepareAnimalObject() {
        Animal animal = Animal.builder()
                .birthDate(BIRTH_DATE)
                .name(NAME)
                .race(RACE)
                .imageUrl(IMAGE_URL)
                .species(SPECIES)
                .shelterJoinDate(SHELTER_JOIN_DATE)
                .sex(SEX)
                .statuses(Collections.singleton(new Status(STATUS, STATUS_START_DATE, null)))
                .user(USER)
                .description(DESCRIPTION)
                .build();
        animal.setSterilised(STERILISED);
        return animal;
    }

}
