package com.pwr.it.app.auth;

import com.pwr.it.app.auth.config.TestSeeder;
import com.pwr.it.app.data.domain.AccountRoles;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@MicronautTest
@Property(name = "micronaut.server.port", value = "-1")
public class AuthenticationTest {

    private static final String USERNAME = "username";
    private static final String CORRECT_PASSWORD = "correct@password";
    private static final String WRONG_PASSWORD = "wrong_password";
    private static final String USER_ROLE = AccountRoles.USER.getName();
    private static final String USER_EMAIL = "email@email.com";
    private static final String USER_PHONE_NUMBER = "+48 999 888 777";
    private static final String USER_ADDRESS = "Wrocłąw";

    @Inject
    private EmbeddedServer server;

    @Inject
    @Client("/")
    private RxHttpClient httpClient;

    @Inject
    private TestSeeder testSeeder;

    @PostConstruct
    public void init() {
        testSeeder.initSimpleUser(USERNAME, CORRECT_PASSWORD, USER_ROLE, USER_ADDRESS, USER_PHONE_NUMBER, USER_EMAIL);
    }

    @Test
    public void mustLoginWithValidCredentials() {
        UsernamePasswordCredentials upc = new UsernamePasswordCredentials(USERNAME, CORRECT_PASSWORD);
        HttpRequest loginRequest = HttpRequest.POST("/auth/login", upc);
        HttpResponse<BearerAccessRefreshToken> response = httpClient.toBlocking().exchange(loginRequest, BearerAccessRefreshToken.class);

        assertThat(response.getStatus().getCode()).isEqualTo(200);
        assertThat(response.getBody().get().getUsername()).isEqualTo(USERNAME);
    }

    @Test
    public void mustNotLoginWithInvalidCredentials() {
        try {
            UsernamePasswordCredentials upc = new UsernamePasswordCredentials(USERNAME, WRONG_PASSWORD);
            HttpRequest loginRequest = HttpRequest.POST("/login", upc);
            HttpResponse<BearerAccessRefreshToken> response = httpClient.toBlocking().exchange(loginRequest, BearerAccessRefreshToken.class);
            fail();
        } catch (HttpClientResponseException ex) {
            assertThat(ex.getStatus().getCode()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
        }
    }

}
