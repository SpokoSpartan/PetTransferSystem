package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.User;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(
            value = "SELECT * FROM \"user\" where full_name = :pattern " +
                    "                        OR phone_number = :pattern " +
                    "                        OR email = :pattern " +
                    "                        LIMIT 10",
            nativeQuery = true)
    List<User> findByFullNameOrPhoneNumberOrEmail(String pattern);

    Optional<User> findByFullName(String fullName);

    @Query(
            value = "select * from \"user\" where users_organizations IN (" +
                    "select users_organizations from \"user\" where id = :userId)",
            nativeQuery = true
    )
    List<User> findAllUserInTheSameOrganization(Long userId);

}
