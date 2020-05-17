package com.pwr.it.app.data.seeder;

import com.pwr.it.app.auth.config.CustomPasswordEncoder;
import com.pwr.it.app.data.domain.AccountRoles;
import com.pwr.it.app.data.domain.UserAccount;
import com.pwr.it.app.data.repository.UserAccountRepository;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class UserAccountSeeder implements ApplicationEventListener<ServerStartupEvent> {

    private final UserAccountRepository userAccountRepository;
    private final CustomPasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ServerStartupEvent event) {
        if (!userAccountRepository.findByLogin("admin").isPresent()) {
            UserAccount account = new UserAccount(
                    "admin",
                    passwordEncoder.encode("admin"),
                    AccountRoles.ADMIN.getName(),
                    "Wrocław Dlugosza 31",
                    "+48 444 555 666",
                    "wojtek@email.com");
            userAccountRepository.save(account);
        }
        if (!userAccountRepository.findByLogin("user").isPresent()) {
            UserAccount account = new UserAccount(
                    "user",
                    passwordEncoder.encode("user"),
                    AccountRoles.USER.getName(),
                    "Krakow, Spokojna 11",
                    "+48 111 222 333",
                    "user@email.com");
            userAccountRepository.save(account);
        }
    }

}
