package com.pwr.it.app.data.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Adopter {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String address;
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private Date adoptionTime;
    @Setter(AccessLevel.NONE)
    private String uuid = UUID.randomUUID().toString();

    public Adopter(String fullName, String phoneNumber, String address) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return uuid.equals(((Adopter) o).uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
