package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.Species;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface SpeciesRepository extends CrudRepository<Species, Long> {

    Optional<Species> findByName(String name);

}
