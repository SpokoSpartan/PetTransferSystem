package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.Status;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {
}
