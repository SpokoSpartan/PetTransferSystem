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
                @Server(url = "http://3.83.87.216:80", description = "AWS"),
                @Server(url = "http://localhost:8081", description = "DEV")
        }
)
public class PetTransferSystemApplication {

    public static void main(String[] args) {
        Micronaut.run(PetTransferSystemApplication.class);
    }

}
