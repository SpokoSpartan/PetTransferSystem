package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.Animal;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long> {
}
