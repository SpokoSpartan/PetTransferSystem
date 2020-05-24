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
        if (!userAccountRepository.findByLogin("Agnieszka").isPresent()) {
            UserAccount account = new UserAccount(
                    "Agnieszka",
                    passwordEncoder.encode("Agnieszka"),
                    AccountRoles.USER.getName(),
                    "Wrocław, ul. Wrocławska 12",
                    "+48 546 790 126",
                    "agmieszka@email.com");
            userAccountRepository.save(account);
        }
        if (!userAccountRepository.findByLogin("Jacek").isPresent()) {
            UserAccount account = new UserAccount(
                    "Jacek",
                    passwordEncoder.encode("Jacek"),
                    AccountRoles.USER.getName(),
                    "Wrocław, ul. Krakowska 11",
                    "+48 223 445 667",
                    "jacek@email.com");
            userAccountRepository.save(account);
        }
        if (!userAccountRepository.findByLogin("Jani").isPresent()) {
            UserAccount account = new UserAccount(
                    "Jani",
                    passwordEncoder.encode("Jani"),
                    AccountRoles.USER.getName(),
                    "Wrocław, ul. Poznańska 245",
                    "+48 111 221 423",
                    "jani@email.com");
            userAccountRepository.save(account);
        }
        if (!userAccountRepository.findByLogin("Piotr").isPresent()) {
            UserAccount account = new UserAccount(
                    "Piotr",
                    passwordEncoder.encode("Piotr"),
                    AccountRoles.USER.getName(),
                    "Poznań, ul. Poznańska 4A",
                    "+48 142 563 534",
                    "piotr@email.com");
            userAccountRepository.save(account);
        }
        if (!userAccountRepository.findByLogin("Wojtek").isPresent()) {
            UserAccount account = new UserAccount(
                    "Wojtek",
                    passwordEncoder.encode("Wojtek"),
                    AccountRoles.USER.getName(),
                    "Poznań, ul. Szyszkowa 121",
                    "+48 643 554 756",
                    "wojtek@email.com");
            userAccountRepository.save(account);
        }
        if (!userAccountRepository.findByLogin("Kacper").isPresent()) {
            UserAccount account = new UserAccount(
                    "Kacper",
                    passwordEncoder.encode("Kacper"),
                    AccountRoles.USER.getName(),
                    "Poznań, ul. Traugutta 98/16",
                    "+48 756 635 785",
                    "kacper@email.com");
            userAccountRepository.save(account);
        }
    }

}
