package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.Species;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface SpeciesRepository extends CrudRepository<Species, Long> {
}
