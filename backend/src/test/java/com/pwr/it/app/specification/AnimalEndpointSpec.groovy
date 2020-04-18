package com.pwr.it.app.specification

import com.jayway.jsonpath.JsonPath
import com.pwr.it.app.data.domain.*
import com.pwr.it.app.data.seeder.*
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import org.joda.time.DateTime
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

import static com.pwr.it.app.data.domain.AnimalStatus.NEW_IN_SHELTER

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
    @Client('/')
    RxHttpClient client

    @Shared
    Long animalId

    def setupSpec() {
        cleanDatabase()
        animalId = initDog()
    }

    @Unroll
    def "endpoint return pageable object with one animal and correct statistics"() {
        setup:
        def responseAsJson = client.toBlocking().retrieve(HttpRequest.GET('/api/animal/all?page=0&size=2'), String)

        expect:
        JsonPath.read(responseAsJson, testedElement) == correctElement

        where:
        testedElement         | correctElement
        "content[0].id"       | animalId
        "content[0].name"     | "Mała"
        "content[0].location" | "Wrocław ul. Sikorskiego 31"
        "content[0].status"   | "NEW_IN_SHELTER"

        "totalSize"           | 1
        "totalPages"          | 1
        "pageNumber"          | 0
        "numberOfElements"    | 1
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
        "status"                            | "NEW_IN_SHELTER"
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
        Set<Status> huskyDogStatuses = statusSeeder.initAnimalStatuses(dateBackward(730), NEW_IN_SHELTER)
        Status huskyDogStatus = getStatusByName(huskyDogStatuses, NEW_IN_SHELTER)
        Set<TreatmentHistory> huskyDogTreatmentHistory = treatmentHistorySeeder.initChipImplantation(
                huskyDogStatus.getStatusBeginning(), huskyDogStatus.getStatusEnd())
        Animal animal = animalSeeder.initHuskyDog(dog, husky, huskyDogStatuses, user, huskyDogTreatmentHistory)
        return animal.getId()
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

}
