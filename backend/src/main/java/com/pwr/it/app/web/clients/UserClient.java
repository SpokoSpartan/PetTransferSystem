package com.pwr.it.app.web.clients;

import com.pwr.it.app.data.domain.dto.request.UserAccountDetails;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserClient {

    @Inject
    @Client("http://52.91.229.171:80/auth")
    private RxHttpClient client;

    public UserAccountDetails getUserDetails(String name) {
        return client.toBlocking().retrieve(HttpRequest.GET("/user/" + name), UserAccountDetails.class);
    }

}
