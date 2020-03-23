package com.pwr.it.app.data.repository;

import com.pwr.it.app.data.domain.TreatmentHistory;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface TreatmentHistoryRepository extends CrudRepository<TreatmentHistory, Long> {
}
