package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.Adopter;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, Long> {
}
