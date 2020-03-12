package com.pwr.it.app.data.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private AnimalStatus animalStatus;
    private Date statusBeginning;
    private Date statusEnd;
    private String uuid = UUID.randomUUID().toString();

    public Status(AnimalStatus animalStatus, Date statusBeginning, Date statusEnd) {
        this.animalStatus = animalStatus;
        this.statusBeginning = statusBeginning;
        this.statusEnd = statusEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return uuid.equals(((Status) o).uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
