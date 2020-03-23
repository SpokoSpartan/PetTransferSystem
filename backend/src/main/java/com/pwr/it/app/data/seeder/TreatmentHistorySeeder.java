package com.pwr.it.app.data.seeder;

import com.pwr.it.app.data.domain.TreatmentHistory;
import com.pwr.it.app.data.repository.TreatmentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.Set;

@Singleton
@RequiredArgsConstructor
public class TreatmentHistorySeeder {

    private final TreatmentHistoryRepository treatmentHistoryRepository;

    public Set<TreatmentHistory> initPreventiveExamination(Date startDate, Date endDate) {
        Date examinationStartDate = getRandomDateBetween(startDate, endDate);
        Date examinationEndDate = getRandomDateBetween(examinationStartDate, endDate);
        TreatmentHistory examination = new TreatmentHistory(
                "Preventive examination",
                "Dr. Gregory House",
                examinationStartDate,
                examinationEndDate,
                "Preventive examination and observation.",
                new BigDecimal("100.20"),
                "PROVET Wrocław, ul. Trwała 7");
        return initTreatmentHistory(examination);
    }

    public Set<TreatmentHistory> initChipImplantation(Date startDate, Date endDate) {
        Date examinationStartDate = getRandomDateBetween(startDate, endDate);
        Date examinationEndDate = getRandomDateBetween(examinationStartDate, endDate);
        TreatmentHistory examination = new TreatmentHistory(
                "Chip implantation",
                "Dr. Lisa Cuddy",
                examinationStartDate,
                examinationEndDate,
                "Chip implantation",
                new BigDecimal("200"),
                "Evet. Veterinary clinic. Mieczysława Ledóchowskiego 9/2, 50-325 Wrocław");
        return initTreatmentHistory(examination);
    }

    private Date getRandomDateBetween(Date startDate, Date endDate) {
        DateTime startDateTime = new DateTime(startDate);
        int daysBetweenBeginAndEnd = Days.daysBetween(startDateTime, new DateTime(endDate)).getDays();
        return startDateTime.plusDays(generateRandomNumberOfDays(daysBetweenBeginAndEnd)).toDate();
    }

    private int generateRandomNumberOfDays(int maxNumber) {
        return new Random().nextInt(maxNumber);
    }

    @Transactional
    private Set<TreatmentHistory> initTreatmentHistory(TreatmentHistory treatmentHistory) {
        return Collections.singleton(treatmentHistoryRepository.save(treatmentHistory));
    }

}
