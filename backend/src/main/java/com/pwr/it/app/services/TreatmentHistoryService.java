package com.pwr.it.app.services;

import com.pwr.it.app.data.domain.Animal;
import com.pwr.it.app.data.domain.TreatmentHistory;
import com.pwr.it.app.data.domain.dto.request.TreatmentRequest;
import com.pwr.it.app.data.repository.TreatmentHistoryRepository;
import com.pwr.it.app.web.exception.AnimalNotFoundException;
import com.pwr.it.app.web.exception.DateValidationException;
import com.pwr.it.app.web.exception.TreatmentNotFoundException;
import lombok.RequiredArgsConstructor;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor
public class TreatmentHistoryService {

    private final TreatmentHistoryRepository treatmentHistoryRepository;
    private final AnimalService animalService;

    @Transactional
    public void addTreatmentToAnimal(TreatmentRequest request, long animalId) throws AnimalNotFoundException, DateValidationException {
        validateDates(request);
        Animal animal = animalService.getAnimalById(animalId);
        animal.getTreatmentHistories().add(addTreatmentHistory(request));
    }

    private TreatmentHistory addTreatmentHistory(TreatmentRequest request) {
        TreatmentHistory treatmentHistory = new TreatmentHistory(
                request.getTitle(),
                request.getDoctorName(),
                request.getStartDate(),
                request.getEndDate(),
                request.getDescription(),
                request.getPrice(),
                request.getPlace());
        return treatmentHistoryRepository.save(treatmentHistory);
    }

    @Transactional
    public void updateTreatment(TreatmentRequest request, long id) throws DateValidationException, TreatmentNotFoundException {
        validateDates(request);
        TreatmentHistory treatment = getTreatmentById(id);
        treatment.setTitle(request.getTitle());
        treatment.setStartDate(request.getStartDate());
        treatment.setEndDate(request.getEndDate());
        treatment.setPlace(request.getPlace());
        treatment.setPrice(request.getPrice());
        treatment.setDoctorName(request.getDoctorName());
        treatment.setDescription(request.getDescription());
    }

    private void validateDates(TreatmentRequest request) throws DateValidationException {
        if (request.getEndDate() != null) {
            if (request.getEndDate().before(request.getStartDate())) {
                throw new DateValidationException("Start date mut be before end date");
            }
        }
    }

    @Transactional
    public void removeTreatment(long id) throws TreatmentNotFoundException {
        TreatmentHistory treatment = getTreatmentById(id);
        treatment.setCanceled(true);
    }

    private TreatmentHistory getTreatmentById(long id) throws TreatmentNotFoundException {
        Optional<TreatmentHistory> treatmentHistory = treatmentHistoryRepository.findById(id);
        if (!treatmentHistory.isPresent() || treatmentHistory.get().getCanceled()) {
            throw new TreatmentNotFoundException();
        }
        return treatmentHistory.get();
    }

}
