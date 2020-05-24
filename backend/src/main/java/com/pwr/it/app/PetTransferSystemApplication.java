package com.pwr.it.app;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@SecurityScheme(
        name = "Oauth2",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                password = @OAuthFlow(
                        tokenUrl = "http://localhost:8090/auth/login",
                        refreshUrl = "http://localhost:8090/auth/login"
                )
        )
)
@OpenAPIDefinition(
        info = @Info(
                title = "Pet Transfer System",
                version = "0.0.1-SNAPSHOT"
        ),
        servers = {
                @Server(url = "http://52.91.229.171:80", description = "AWS"),
                @Server(url = "http://localhost:8081", description = "DEV")
        },
        security = @SecurityRequirement(
                name = "Oauth2"
        )
)
public class PetTransferSystemApplication {

    public static void main(String[] args) {
        Micronaut.run(PetTransferSystemApplication.class);
    }

}
