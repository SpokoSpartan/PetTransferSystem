package com.pwr.it.app.web.controller;


import com.pwr.it.app.data.service.AuthService;
import com.pwr.it.app.web.dto.UserAccountDetails;
import com.pwr.it.app.web.exception.UserAlreadyExistsException;
import com.pwr.it.app.web.exception.UserNotFoundException;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.validation.Valid;

@Singleton
@Controller
@RequiredArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED)
public class AuthController {

    private final AuthService authService;

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/auth/register")
    public void registerUser(@Valid @Body UserAccountDetails accountDetails) throws UserAlreadyExistsException {
        authService.registerUser(accountDetails);
    }

    //Available only internally
    @Get("/user/{name}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public UserAccountDetails getUserDetails(@PathVariable String name) throws UserNotFoundException {
        return authService.getUser(name);
    }

}
