package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.User;
import com.pwr.it.app.data.repository.UserRepository;
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
}
