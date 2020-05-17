package com.pwr.it.app.auth.config;

import com.pwr.it.app.data.repository.UserAccountRepository;
import com.pwr.it.app.data.domain.UserAccount;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
public class TestSeeder {

    private final UserAccountRepository accountRepository;
    private final CustomPasswordEncoder passwordEncoder;

    public UserAccount initSimpleUser(String username, String password, String role, String address, String phoneNumber, String email) {
        Optional<UserAccount> account = accountRepository.findByLogin(username);
        return account.orElseGet(() ->
                accountRepository.save(new UserAccount(
                        username,
                        passwordEncoder.encode(password),
                        role,
                        address,
                        phoneNumber,
                        email)));
    }

}
