package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.Organization;
import com.pwr.it.app.data.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;

@Singleton
@RequiredArgsConstructor
public class OrganizationSeeder {

	private final OrganizationRepository organizationRepository;

	public Organization initSimpleAOrganization() {
		Organization organization = new Organization(
				"ASYLUS",
				"+48 696 221 450",
				"asylus@gmail.com",
				"Wrocław ul. Sikorskiego 31");
		return initOrganization(organization);
	}

	private Organization initOrganization(Organization organization) {
		return organizationRepository.save(organization);
	}
}
