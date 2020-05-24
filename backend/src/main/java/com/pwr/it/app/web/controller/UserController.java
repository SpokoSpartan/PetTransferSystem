package com.pwr.it.app.web.controller;

import com.pwr.it.app.data.domain.dto.response.UserResponse;
import com.pwr.it.app.services.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "User")
@Controller("/api/user")
@RequiredArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED)
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Find users by pattern",
            description = "Find user by full name, email " +
                    "or phone number by using pattern."
    )
    @Get("/find/{pattern}")
    public List<UserResponse> findUsers(@PathVariable String pattern) {
        return userService.findByPattern(pattern);
    }

}
