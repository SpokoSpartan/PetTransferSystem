package com.pwr.it.app.specification

import com.jayway.jsonpath.JsonPath
import com.pwr.it.app.data.seeder.DatabaseCleaner
import com.pwr.it.app.data.seeder.RaceSeeder
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

@MicronautTest
class RaceEndpointSpec extends Specification {

    @Inject
    RaceSeeder raceSeeder;

    @Inject
    @Shared
    DatabaseCleaner databaseCleaner

    @Inject
    @Client('/')
    RxHttpClient client

    def setupSpec() {
        cleanDatabase()
        initRaces()
    }

    @Unroll
    def "endpoint return list of races sorted alphabetically"() {
        setup:
        def responseAsJson = client.toBlocking().retrieve(HttpRequest.GET('/api/race/all'), String)

        expect:
        JsonPath.read(responseAsJson, testedElement) == correctElement

        where:
        testedElement | correctElement
        "[0]"         | "Bulldog"
        "[1]"         | "Persian cat"
        "[2]"         | "Royal python"
        "[3]"         | "Siamese cat"
        "[4]"         | "Siberian Husky"
    }

    private cleanDatabase() {
        databaseCleaner.clean()
    }

    private initRaces() {
        raceSeeder.initSiberianHusky()
        raceSeeder.initBulldog()
        raceSeeder.initRoyalPython()
        raceSeeder.initPersianCat()
        raceSeeder.initSiameseCat()
    }

}