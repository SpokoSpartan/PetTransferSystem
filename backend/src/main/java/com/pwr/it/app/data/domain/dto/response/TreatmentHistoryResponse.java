package com.pwr.it.app.data.domain.dto.response;


import io.micronaut.core.annotation.Introspected;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Introspected
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TreatmentHistoryResponse implements Comparable<TreatmentHistoryResponse> {

    private Long id;
    private String doctor;
    private Date startDate;
    private Date endDate;
    private String title;
    private String description;
    private BigDecimal price;
    private String place;
    private String treatmentStatus;

    @Override
    public int compareTo(TreatmentHistoryResponse treatmentHistoryResponse) {
        return this.startDate.compareTo(treatmentHistoryResponse.getStartDate());
    }

}
