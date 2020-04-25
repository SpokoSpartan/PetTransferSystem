package com.pwr.it.app.specification

import com.jayway.jsonpath.JsonPath
import com.pwr.it.app.data.domain.*
import com.pwr.it.app.data.seeder.*
import com.pwr.it.app.services.UserService
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import org.joda.time.DateTime
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

import static com.pwr.it.app.data.domain.AnimalStatus.NEW_IN_SHELTER
import static com.pwr.it.app.data.domain.AnimalStatus.READY_FOR_ADOPTION
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

@MicronautTest
class AnimalEndpointSpec extends Specification {

    @Inject
    OrganizationSeeder organizationSeeder
    @Inject
    SpeciesSeeder speciesSeeder
    @Inject
    UserSeeder userSeeder
    @Inject
    RaceSeeder raceSeeder
    @Inject
    StatusSeeder statusSeeder
    @Inject
    TreatmentHistorySeeder treatmentHistorySeeder
    @Inject
    AnimalSeeder animalSeeder

    @Inject
    @Shared
    DatabaseCleaner databaseCleaner

    @Inject
    UserService userService

    @Inject
    @Client('/')
    RxHttpClient client

    @Shared
    Long animalId

    @Shared
    User loggedUser

    def setupSpec() {
        cleanDatabase()
        initDog()
    }

    @Unroll
    def "endpoint return pageable of animal prepared for adoption with one object and correct statistics"() {
        setup:
        def responseAsJson = client.toBlocking().retrieve(HttpRequest.GET('/api/animal/all?page=0&size=2'), String)

        expect:
        JsonPath.read(responseAsJson, testedElement) == correctElement

        where:
        testedElement         | correctElement
        "content[0].id"       | animalId
        "content[0].name"     | "Mała"
        "content[0].location" | "Wrocław ul. Sikorskiego 31"
        "content[0].status"   | "READY_FOR_ADOPTION"

        "totalSize"           | 1
        "totalPages"          | 1
        "pageNumber"          | 0
        "numberOfElements"    | 1
        "size"                | 2
    }

    @Unroll
    def "endpoint return pageable animal that belong to user with one object and correct statistics"() {
        setup:
        when(userService.getLoggedUser()).thenReturn(loggedUser)

        and:
        def responseAsJson = client.toBlocking().retrieve(HttpRequest.GET('/api/animal/my/all?page=0&size=2'), String)

        expect:
        JsonPath.read(responseAsJson, testedElement) == correctElement

        where:
        testedElement         | correctElement
        "content[0].id"       | animalId
        "content[0].name"     | "Mała"
        "content[0].location" | "Wrocław ul. Sikorskiego 31"
        "content[0].status"   | "READY_FOR_ADOPTION"

        "totalSize"           | 1
        "totalPages"          | 1
        "pageNumber"          | 0
        "numberOfElements"    | 1
        "size"                | 2
    }

    @Unroll
    def "endpoint return pageable animal that not belong to user without animal and correct statistics"() {
        setup:
        User user = mock(User.class)
        when(user.getId()).thenReturn(0L)
        when(userService.getLoggedUser()).thenReturn(user)

        and:
        def responseAsJson = client.toBlocking().retrieve(HttpRequest.GET('/api/animal/my/all?page=0&size=2'), String)

        expect:
        JsonPath.read(responseAsJson, testedElement) == correctElement

        where:
        testedElement         | correctElement
        "totalSize"           | 0
        "totalPages"          | 0
        "pageNumber"          | 0
        "numberOfElements"    | 0
        "size"                | 2
    }

    @Unroll
    def "endpoint return single animal with correctly formed data"() {
        setup:
        def responseAsJson = client.toBlocking().retrieve(HttpRequest.GET('/api/animal/one/' + animalId), String)

        expect:
        JsonPath.read(responseAsJson, testedElement) == correctElement

        where:
        testedElement                       | correctElement
        "id"                                | animalId
        "name"                              | "Mała"
        "species"                           | "dog"
        "race"                              | "Siberian Husky"
        "status"                            | "READY_FOR_ADOPTION"
        "sex"                               | "female"
        "sterilised"                        | false

        "animalLocation.fullName"           | "ASYLUS"
        "animalLocation.phone"              | "+48 696 221 450"
        "animalLocation.email"              | "asylus@gmail.com"
        "animalLocation.address"            | "Wrocław ul. Sikorskiego 31"
        "animalLocation.locationType"       | "ORGANIZATION"

        "treatmentHistories[0].doctor"      | "Dr. Lisa Cuddy"
        "treatmentHistories[0].title"       | "Chip implantation"
        "treatmentHistories[0].description" | "Chip implantation"
        "treatmentHistories[0].price"       | 200
        "treatmentHistories[0].place"       | "Evet. Veterinary clinic. Mieczysława Ledóchowskiego 9/2, 50-325 Wrocław"
    }

    private cleanDatabase() {
        databaseCleaner.clean()
    }

    private initDog() {
        Organization simpleOrganization = organizationSeeder.initSimpleAOrganization()
        Species dog = speciesSeeder.initDog()
        User user = userSeeder.initJani(Optional.of(simpleOrganization))
        Race husky = raceSeeder.initSiberianHusky()
        Set<Status> huskyDogStatuses = statusSeeder.initAnimalStatuses(dateBackward(730), NEW_IN_SHELTER, READY_FOR_ADOPTION)
        Status huskyDogStatus = getStatusByName(huskyDogStatuses, READY_FOR_ADOPTION)
        Set<TreatmentHistory> huskyDogTreatmentHistory = treatmentHistorySeeder.initChipImplantation(
                huskyDogStatus.getStatusBeginning(), huskyDogStatus.getStatusEnd())
        Animal animal = animalSeeder.initHuskyDog(dog, husky, huskyDogStatuses, user, huskyDogTreatmentHistory)

        animalId = animal.getId()
        loggedUser = user
    }

    private static dateBackward(int days) {
        return DateTime.now().minusDays(days).toDate()
    }

    private static getStatusByName(Set<Status> statuses, AnimalStatus animalStatus) {
        return statuses.stream()
                .filter({ status -> (status.getAnimalStatus() == animalStatus) })
                .findFirst()
                .orElseThrow({ -> new IllegalArgumentException("Status with name " + animalStatus + " not found") })
    }


    @MockBean(UserService.class)
    UserService userService() {
        return mock(UserService.class)
    }


}
