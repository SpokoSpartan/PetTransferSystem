package com.pwr.it.app.specification

import com.jayway.jsonpath.JsonPath
import com.pwr.it.app.data.seeder.DatabaseCleaner
import com.pwr.it.app.data.seeder.SpeciesSeeder
import io.micronaut.context.annotation.Property
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import javax.inject.Inject

@MicronautTest
@Property(name = "micronaut.server.port", value = "-1")
@Property(name = "micronaut.security.enabled", value = "false")
class SpeciesEndpointSpec extends Specification {

    @Inject
    SpeciesSeeder speciesSeeder

    @Inject
    @Shared
    DatabaseCleaner databaseCleaner

    @Inject
    @Client('/')
    RxHttpClient client

    def setupSpec() {
        cleanDatabase()
        initSpecies()
    }

    @Unroll
    def "endpoint return list of species sorted alphabetically"() {
        setup:
        def responseAsJson = client.toBlocking().retrieve(HttpRequest.GET('/api/species/all'), String)

        expect:
        JsonPath.read(responseAsJson, testedElement) == correctElement

        where:
        testedElement | correctElement
        "[0]"         | "cat"
        "[1]"         | "dog"
        "[2]"         | "snake"
    }

    private cleanDatabase() {
        databaseCleaner.clean()
    }

    private initSpecies() {
        speciesSeeder.initCat()
        speciesSeeder.initDog()
        speciesSeeder.initSnake()
    }

}
