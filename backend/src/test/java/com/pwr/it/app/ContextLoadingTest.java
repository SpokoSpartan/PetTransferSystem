package com.pwr.it.app;

import io.micronaut.context.annotation.Property;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

@MicronautTest
@Property(name = "micronaut.server.port", value = "-1")
@Property(name = "micronaut.security.enabled", value = "false")
public class ContextLoadingTest {

    @Test
    void shouldStartMicronautContext() {
        //This test is running micronaut context
    }

}
