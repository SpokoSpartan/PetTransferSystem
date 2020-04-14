package com.pwr.it.app.data.domain;

import com.pwr.it.app.data.domain.dto.response.TreatmentHistoryResponse;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class TreatmentHistoryTest {

    private static final String TITLE = "treatment-title";
    private static final String DOCTOR_NAME = "Dr. Oetker";
    private static final Date START_DATE = new Date(123456789);
    private static final Date END_DATE = new Date(234567890);
    private static final String DESCRIPTION = "Operation";
    private static final BigDecimal PRICE = new BigDecimal("200.00");
    private static final String PLACE = "Wrocław";

    @Test
    void shouldCorrectlyTranslateTreatmentHistory() {
        TreatmentHistory treatmentHistory = prepareTreatmentHistory();

        TreatmentHistoryResponse response = treatmentHistory.translateToTreatmentHistoryResponse();

        assertAll(
                () -> assertEquals(TITLE, response.getTitle()),
                () -> assertEquals(DOCTOR_NAME, response.getDoctor()),
                () -> assertEquals(START_DATE, response.getStartDate()),
                () -> assertEquals(END_DATE, response.getEndDate()),
                () -> assertEquals(DESCRIPTION, response.getDescription()),
                () -> assertEquals(PRICE, response.getPrice()),
                () -> assertEquals(PLACE, response.getPlace())
        );
    }

    private TreatmentHistory prepareTreatmentHistory() {
        return new TreatmentHistory(TITLE, DOCTOR_NAME, START_DATE, END_DATE, DESCRIPTION, PRICE, PLACE);
    }

}
