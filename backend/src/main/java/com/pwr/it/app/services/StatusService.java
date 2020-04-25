package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.AnimalStatus;
import com.pwr.it.app.data.domain.Status;
import com.pwr.it.app.data.repository.StatusRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import java.util.Date;

@Singleton
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public Status addNewStatus(AnimalStatus animalStatus, Date statusBeginning) {
        Status status = new Status(animalStatus, statusBeginning);
        return statusRepository.save(status);
    }
}
