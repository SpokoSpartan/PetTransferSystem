package com.pwr.it.app.web.controller;


import com.pwr.it.app.data.service.AuthService;
import com.pwr.it.app.web.dto.UserAccountDetails;
import com.pwr.it.app.web.exception.UserNotFoundException;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.validation.Valid;

@Singleton
@Controller("/api/auth")
@RequiredArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED)
public class AuthController {

    private final AuthService authService;

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/register")
    public void registerUser(@Valid @Body UserAccountDetails accountDetails) {
        authService.registerUser(accountDetails);
    }

    @Get("/user")
    public UserAccountDetails getUserDetails(Authentication authentication) throws UserNotFoundException {
        return authService.getUser(authentication);
    }

}
