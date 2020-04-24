package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.Animal;
import com.pwr.it.app.data.domain.CountObjects;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long> {

    @Query(value = "SELECT a.* from animal a INNER JOIN status s " +
            "ON (a.id = s.animals_status) WHERE s.status_end IS NULL AND s.animal_status = 1 " +
            "ORDER BY a.id LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    Iterable<Animal> findAllReadyForAdoption(int limit, int offset);

    @Query(value = "SELECT count(*) AS count from animal a INNER JOIN status s " +
            "ON (a.id = s.animals_status) WHERE s.status_end IS NULL AND s.animal_status = 1",
            nativeQuery = true)
    CountObjects countAnimalForAdoption();

    @Query(value = "SELECT a.* FROM animal a WHERE animals_user = :userId " +
            "ORDER BY a.id LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    Iterable<Animal> findAllOwnedByUser(long userId, int limit, int offset);

    @Query(value = "SELECT count(*) AS count FROM animal a WHERE animals_user = :userId",
            nativeQuery = true)
    CountObjects countAnimalsOwnedByUser(long userId);

}
