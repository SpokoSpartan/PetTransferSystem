package com.pwr.it.app.data.service;

import com.pwr.it.app.auth.config.CustomPasswordEncoder;
import com.pwr.it.app.data.domain.AccountRoles;
import com.pwr.it.app.data.domain.UserAccount;
import com.pwr.it.app.data.repository.UserAccountRepository;
import com.pwr.it.app.web.dto.UserAccountDetails;
import com.pwr.it.app.web.exception.UserAlreadyExistsException;
import com.pwr.it.app.web.exception.UserNotFoundException;
import io.micronaut.security.authentication.Authentication;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@RequiredArgsConstructor
public class AuthService {

    private final UserAccountRepository userAccountRepository;
    private final CustomPasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(UserAccountDetails request) throws UserAlreadyExistsException {
        if (userAccountRepository.findByLogin(request.getFullName()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        UserAccount account = new UserAccount(
                request.getFullName(),
                passwordEncoder.encode(request.getPassword()),
                AccountRoles.USER.getName(),
                request.getAddress(),
                request.getPhoneNumber(),
                request.getEmail());
        userAccountRepository.save(account);
    }

    @Transactional
    public UserAccountDetails getUser(String name) throws UserNotFoundException {
        UserAccount account = userAccountRepository.findByLogin(name)
                .orElseThrow(() -> new UserNotFoundException());
        return account.translateToDetails();
    }

}
