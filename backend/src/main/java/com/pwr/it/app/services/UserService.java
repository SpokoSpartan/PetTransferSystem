package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.User;
import com.pwr.it.app.data.domain.dto.response.UserResponse;
import com.pwr.it.app.data.repository.UserRepository;
import com.pwr.it.app.web.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // TODO return fake user
    public User getLoggedUser() {
        return getAllUsers().get(0);
    }

    private List<User> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public User getUserById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    public List<UserResponse> findByPattern(String pattern) {
        return userRepository.findByFullNameOrPhoneNumberOrEmail(pattern).stream()
                .map(User::translateToResponse)
                .collect(Collectors.toList());
    }

}
