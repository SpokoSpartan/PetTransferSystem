package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.User;
import com.pwr.it.app.data.domain.dto.request.UserAccountDetails;
import com.pwr.it.app.data.domain.dto.response.UserResponse;
import com.pwr.it.app.data.repository.UserRepository;
import com.pwr.it.app.web.clients.UserClient;
import com.pwr.it.app.web.exception.UserNotFoundException;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.UserDetails;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserClient userClient;

    public User getLoggedUser(Authentication authentication) throws UserNotFoundException {
        // if user exists in the database
        Optional<User> user = userRepository.findByFullName(authentication.getName());
        if (user.isPresent()) {
            return user.get();
        }
        //if not then try to check if exists in auth service database
        User newUser = userClient.getUserDetails(authentication.getName()).translateToUser();
        return userRepository.save(newUser);
    }


    public User getUserById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    @Transactional
    public List<UserResponse> findByPattern(String pattern) {
        return userRepository.findByFullNameOrPhoneNumberOrEmail(pattern).stream()
                .map(User::translateToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<UserResponse> findByPatternAndNotInOrganization(String pattern) {
        return userRepository.findByFullNameOrPhoneNumberOrEmail(pattern).stream()
                .filter(user -> !Optional.ofNullable(user.getOrganization()).isPresent())
                .map(User::translateToResponse)
                .collect(Collectors.toList());
    }

}
