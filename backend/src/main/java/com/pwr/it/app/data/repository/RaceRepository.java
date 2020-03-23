package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.Race;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface RaceRepository extends CrudRepository<Race, Long> {
}
