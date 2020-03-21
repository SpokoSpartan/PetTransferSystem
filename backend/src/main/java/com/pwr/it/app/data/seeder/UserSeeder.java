package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.Organization;
import com.pwr.it.app.data.domain.User;
import com.pwr.it.app.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
public class UserSeeder {

	private final UserRepository userRepository;

	public User initAgnieszka(Optional<Organization> organization) {
		User agnieszka = new User("Agnieszka", "+48 546 790 126", "agmieszka@email.com", "Wrocław, ul. Wrocławska 12");
		organization.ifPresent(org -> agnieszka.setOrganization(org));
		return initUser(agnieszka);
	}

	public User initJacek(Optional<Organization> organization) {
		User jacek = new User("Jacek", "+48 223 445 667", "jacek@email.com", "Wrocław, ul. Krakowska 11");
		organization.ifPresent(org -> jacek.setOrganization(org));
		return initUser(jacek);
	}

	public User initJani(Optional<Organization> organization) {
		User jani = new User("Jani", "+48 111 221 423", "jani@email.com", "Wrocław, ul. Poznańska 245");
		organization.ifPresent(org -> jani.setOrganization(org));
		return initUser(jani);
	}

	public User initPiotr(Optional<Organization> organization) {
		User piotr = new User("Piotr", "+48 142 563 534", "piotr@email.com", "Poznań, ul. Poznańska 4A");
		organization.ifPresent(org -> piotr.setOrganization(org));
		return initUser(piotr);
	}

	public User initWojtek(Optional<Organization> organization) {
		User wojtek = new User("Wojtek", "+48 643 554 756", "wojtek@email.com", "Poznań, ul. Szyszkowa 121");
		organization.ifPresent(org -> wojtek.setOrganization(org));
		return initUser(wojtek);
	}

	public User initKacper(Optional<Organization> organization) {
		User kacper = new User("Kacper", "+48 756 635 785", "kacper@email.com", "Poznań, ul. Traugutta 98/16");
		organization.ifPresent(org -> kacper.setOrganization(org));
		return initUser(kacper);
	}

	private User initUser(User user) {
		return userRepository.save(user);
	}
}
