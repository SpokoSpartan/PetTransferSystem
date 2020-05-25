package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.Organization;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    @Query(
            value = "select o.* from organization o inner JOIN \"user\" u " +
                    "on (o.id = u.users_organizations) where u.id = :userId",
            nativeQuery = true
    )
    Optional<Organization> getUserOrganization(Long userId);

}
