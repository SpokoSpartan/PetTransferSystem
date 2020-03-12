package com.pwr.it.app.data.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TreatmentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String doctorName;
    private Date startDate;
    private Date endDate;
    private String description;
    private BigDecimal price;
    private String place;
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
