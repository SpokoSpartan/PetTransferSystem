package com.pwr.it.app.data.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
            CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "animals-species")
    private Species species;
    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "animals-race")
    private Race race;
    @OneToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "animals-status")
    private Set<Status> statuses;
    @Column(length = 1000)
    private String description;
    @Basic
    private Date birthDate;
    private String sex;
    private Boolean sterilised;
    private Date shelterJoinDate;
    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "animals-user")
    private User user;
    @OneToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "animals-treatment-history")
    private Set<TreatmentHistory> treatmentHistories = new HashSet<>();
    private String uuid = UUID.randomUUID().toString();

    @Builder
    public Animal(String name, Species species, Race race, Set<Status> statuses, String description,
                  Date birthDate, String sex, Date shelterJoinDate, User user) {
        this.name = name;
        this.species = species;
        this.race = race;
        this.statuses = statuses;
        this.description = description;
        this.birthDate = birthDate;
        this.sex = sex;
        this.shelterJoinDate = shelterJoinDate;
        this.user = user;
    }

    public void addTreatmentHistory(TreatmentHistory treatmentHistory) {
        this.treatmentHistories.add(treatmentHistory);
    }

    public void setSterilised(boolean sterilised) {
        this.sterilised = sterilised;
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
