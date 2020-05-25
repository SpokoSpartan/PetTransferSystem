package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.Organization;
import com.pwr.it.app.data.domain.User;
import com.pwr.it.app.data.domain.dto.response.UserResponse;
import com.pwr.it.app.data.repository.OrganizationRepository;
import com.pwr.it.app.data.repository.UserRepository;
import com.pwr.it.app.web.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public Long createOrganization(Organization request, User loggedUser) throws UserNotFoundException {
        Organization organization = new Organization(
                request.getFullName(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getAddress());
        organization = organizationRepository.save(organization);
        User user = userService.getUserById(loggedUser.getId());
        user.setOrganization(organization);
        return organization.getId();
    }

    @Transactional
    public List<UserResponse> getUsersInOrganization(User loggedUser) {
        return userRepository.findAllUserInTheSameOrganization(loggedUser.getId())
                .stream().map(User::translateToResponse).collect(Collectors.toList());
    }

    @Transactional
    public Organization getUserOrganization(User loggedUser) {
        return organizationRepository.getUserOrganization(loggedUser.getId())
                .orElseGet(Organization::new);
    }

    @Transactional
    public void addUserToOrganization(Long userId, User loggedUser) throws UserNotFoundException {
        Organization organization = getUserOrganization(loggedUser);
        User user = userService.getUserById(userId);
        user.setOrganization(organization);
    }

    @Transactional
    public void removeUserFromMyOrganization(Long userId, User loggedUser) throws UserNotFoundException {
        Organization organization = getUserOrganization(loggedUser);
        User user = userService.getUserById(userId);
        if (user.getOrganization().equals(organization)) {
            user.setOrganization(null);
        }
    }

}
