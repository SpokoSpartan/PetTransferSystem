package com.pwr.it.app.data.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "animals-species")
    private Species species;
    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "animals-race")
    private Race race;
    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "animals-status")
    private Status status;
    private String description;
    @Basic
    private Date birthDate;
    private String sex;
    private Boolean sterilised;
    private Date shelterJoinDate;
    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "animals-user")
    private User user;
    private String uuid = UUID.randomUUID().toString();

    public Animal(String name, Species species, Race race, Status status, String description,
                  Date birthDate, String sex, Boolean sterilised, Date shelterJoinDate, User user) {
        this.name = name;
        this.species = species;
        this.race = race;
        this.status = status;
        this.description = description;
        this.birthDate = birthDate;
        this.sex = sex;
        this.sterilised = sterilised;
        this.shelterJoinDate = shelterJoinDate;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return uuid.equals(((Animal) o).uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
