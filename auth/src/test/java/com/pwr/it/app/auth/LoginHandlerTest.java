package com.pwr.it.app.auth;

import com.pwr.it.app.auth.config.TestSeeder;
import com.pwr.it.app.data.domain.AccountRoles;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.*;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
@Property(name = "micronaut.server.port", value = "-1")
public class LoginHandlerTest {

    private static final String USERNAME = "username";
    private static final String CORRECT_PASSWORD = "correct@password";
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
    public void customLoginHandler() {
        HttpRequest request = HttpRequest.create(HttpMethod.POST, "/login")
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .body(new UsernamePasswordCredentials(USERNAME, CORRECT_PASSWORD));
        HttpResponse<BearerAccessRefreshToken> response = httpClient.toBlocking().exchange(request, BearerAccessRefreshToken.class);

        assertThat(response.getStatus().getCode()).isEqualTo(200);
        assertThat(response.body()).isNotNull();
        assertThat(response.body().getAccessToken()).isNotNull();
        assertThat(response.body().getRefreshToken()).isNotNull();
        assertThat(response.body().getExpiresIn()).isNotNull();
        assertThat(response.body().getUsername()).isEqualTo(USERNAME);
        assertThat(response.body().getRoles()).contains(USER_ROLE);
        assertThat(response.body().getTokenType()).isEqualTo(HttpHeaderValues.AUTHORIZATION_PREFIX_BEARER);
    }

}
