package com.pwr.it.app.specification

import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification
import spock.lang.Unroll

@MicronautTest
class FirstSpockSpec extends Specification {

    @Unroll
    def "one plus one should equal two"() {
        expect:
        1 + 1 == 2
    }

}
