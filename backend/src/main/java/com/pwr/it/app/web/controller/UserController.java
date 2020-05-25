package com.pwr.it.app.web.controller;

import com.pwr.it.app.data.domain.Organization;
import com.pwr.it.app.data.domain.User;
import com.pwr.it.app.data.domain.dto.response.UserResponse;
import com.pwr.it.app.services.OrganizationService;
import com.pwr.it.app.services.UserService;
import com.pwr.it.app.web.exception.UserNotFoundException;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "User")
@Controller("/api/user")
@RequiredArgsConstructor
@Secured(SecurityRule.IS_AUTHENTICATED)
public class UserController {

    private final UserService userService;
    private final OrganizationService organizationService;

    @Operation(
            summary = "Find users by pattern",
            description = "Find user by full name, email " +
                    "or phone number by using pattern."
    )
    @Get("/find/{pattern}")
    public List<UserResponse> findUsers(@PathVariable String pattern) {
        return userService.findByPattern(pattern);
    }

    @Get("/find/not-in-organization/{pattern}")
    public List<UserResponse> findUsersNotInOrganization(@PathVariable String pattern) {
        return userService.findByPatternAndNotInOrganization(pattern);
    }

    @Post("/organization/create")
    public Long createOrganization(@Valid @Body Organization organization, Authentication authentication) throws UserNotFoundException {
        User user = userService.getLoggedUser(authentication);
        return organizationService.createOrganization(organization, user);
    }

    @Get("/all-in/organization")
    public List<UserResponse> getUsersInOrganization(Authentication authentication) throws UserNotFoundException {
        User user = userService.getLoggedUser(authentication);
        return organizationService.getUsersInOrganization(user);
    }

    @Get("/organization")
    public Organization getUserOrganization(Authentication authentication) throws UserNotFoundException {
        User user = userService.getLoggedUser(authentication);
        return organizationService.getUserOrganization(user);
    }

    @Post("/organization/add-user/{userId}")
    public void addUserToMyOrganization(Long userId, Authentication authentication) throws UserNotFoundException {
        User user = userService.getLoggedUser(authentication);
        organizationService.addUserToOrganization(userId, user);
    }

    @Post("/organization/remove-user/{userId}")
    public void removeUserFromMyOrganization(Long userId, Authentication authentication) throws UserNotFoundException {
        User user = userService.getLoggedUser(authentication);
        organizationService.removeUserFromMyOrganization(userId, user);
    }

}
