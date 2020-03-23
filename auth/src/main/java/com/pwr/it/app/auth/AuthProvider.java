package com.pwr.it.app.auth;

import com.pwr.it.app.auth.config.CustomPasswordEncoder;
import com.pwr.it.app.data.domain.UserAccount;
import com.pwr.it.app.data.repository.UserAccountRepository;
import io.micronaut.security.authentication.*;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final UserAccountRepository userAccountRepository;
    private final CustomPasswordEncoder passwordEncoder;

    @Override
    public Publisher<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        Optional<UserAccount> user = userAccountRepository.findByLogin((String) request.getIdentity());

        if (user.isPresent() && comparePasswords(request.getSecret(), user.get().getPassword())) {
            return Flowable.just(createUserDetails(user.get()));
        }

        return Flowable.just(new AuthenticationFailed());
    }

    private boolean comparePasswords(Object password1, Object password2) {
        return passwordEncoder.matches(String.valueOf(password1), String.valueOf(password2));
    }

    private UserDetails createUserDetails(UserAccount agent) {
        return new UserDetails(agent.getLogin(), Arrays.asList(agent.getRole()));
    }

}
