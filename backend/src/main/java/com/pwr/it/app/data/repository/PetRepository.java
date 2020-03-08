package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.Pet;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {
}
