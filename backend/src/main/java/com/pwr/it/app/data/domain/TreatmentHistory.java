package com.pwr.it.app.data.domain;

import com.pwr.it.app.data.domain.dto.response.TreatmentHistoryResponse;
import com.pwr.it.app.data.domain.dto.response.TreatmentStatus;
import com.pwr.it.app.data.repository.TreatmentHistoryRepository;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TreatmentHistory {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String doctorName;
    private Date startDate;
    private Date endDate;
    private String description;
    private BigDecimal price;
    private String place;
    @Setter(AccessLevel.NONE)
    private String uuid = UUID.randomUUID().toString();

    public TreatmentHistory(String title,String doctorName, Date startDate,Date endDate,String description, BigDecimal price, String place) {
        this.title = title;
        this.doctorName = doctorName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.price = price;
        this.place = place;
    }

    public TreatmentHistoryResponse translateToTreatmentHistoryResponse() {
        return TreatmentHistoryResponse.builder()
                .id(this.id)
                .doctor(this.doctorName)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .title(this.title)
                .description(this.description)
                .price(this.price)
                .place(this.place)
                .treatmentStatus(prepareTreatmentStatus().toString())
                .build();
    }

    private TreatmentStatus prepareTreatmentStatus() {
        Date now = new Date();
        if (startDate.after(now)) {
            return TreatmentStatus.PLANNED;
        }
        if (endDate != null && endDate.after(now)) {
            return TreatmentStatus.IN_PROGRESS;
        }
        if (endDate != null && endDate.before(now)) {
            return TreatmentStatus.HISTORY;
        }
        return TreatmentStatus.UNKNOWN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return uuid.equals(((TreatmentHistory) o).uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
