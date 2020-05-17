package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.Animal;
import com.pwr.it.app.data.domain.AnimalStatus;
import com.pwr.it.app.data.domain.Status;
import com.pwr.it.app.data.domain.dto.response.StatusResponse;
import com.pwr.it.app.data.repository.AnimalRepository;
import com.pwr.it.app.data.repository.StatusRepository;
import com.pwr.it.app.web.exception.AnimalNotFoundException;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;
    private final AnimalRepository animalRepository;

    @Transactional
    public void changeAnimalStatus(long id, AnimalStatus status) throws AnimalNotFoundException {
        Animal animal = getAnimalById(id);
        Set<Status> animalStatuses = animal.getStatuses();
        if (!animalStatuses.isEmpty()) {
            animalStatuses.stream()
                    .filter(s -> s.getStatusEnd() == null)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Last status not found"))
                    .setStatusEnd(new Date());
        }
        Status as = addNewStatus(status, new Date());
        animalStatuses.add(as);
    }

    @Transactional
    public List<StatusResponse> getAnimalStatusesHistory(long animalId) throws AnimalNotFoundException {
        Animal animal = getAnimalById(animalId);
        return animal.getStatuses().stream()
                .map(Status::translateToResponse)
                .sorted()
                .collect(Collectors.toList());
    }

    public Animal getAnimalById(long id) throws AnimalNotFoundException {
        return animalRepository.findById(id).orElseThrow(() -> new AnimalNotFoundException());
    }

    public Status addNewStatus(AnimalStatus animalStatus, Date statusBeginning) {
        Status status = new Status(animalStatus, statusBeginning);
        return statusRepository.save(status);
    }

}
