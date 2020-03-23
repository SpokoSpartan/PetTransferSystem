package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.Organization;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {
}
