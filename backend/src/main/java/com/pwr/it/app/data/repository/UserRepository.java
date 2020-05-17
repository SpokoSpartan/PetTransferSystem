package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.User;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(
            value = "SELECT * FROM \"user\" where full_name = :pattern " +
                    "                        OR phone_number = :pattern " +
                    "                        OR email = :pattern " +
                    "                        LIMIT 10",
            nativeQuery = true)
    List<User> findByFullNameOrPhoneNumberOrEmail(String pattern);

}
