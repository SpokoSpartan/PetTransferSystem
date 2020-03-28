package com.pwr.it.app;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Pet Transfer System",
                version = "0.0.1-SNAPSHOT"
        ),
        servers = {
                @Server(url = "http://localhost:8081", description = "DEV"),
                @Server(url = "http://34.207.67.160:80", description = "AWS")
        }
)
public class PetTransferSystemApplication {

    public static void main(String[] args) {
        Micronaut.run(PetTransferSystemApplication.class);
    }

}
