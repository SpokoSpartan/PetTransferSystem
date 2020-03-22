package com.pwr.it.app.data.domain;

import com.pwr.it.app.data.domain.dto.response.AnimalDetailsResponse;
import com.pwr.it.app.data.domain.dto.response.AnimalLocationResponse;
import com.pwr.it.app.data.domain.dto.response.AnimalLocationType;
import com.pwr.it.app.data.domain.dto.response.AnimalResponse;
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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
            fetch = FetchType.EAGER)
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

    public AnimalResponse translateToAnimalResponse() {
        return AnimalResponse.builder()
                .id(this.id)
                .name(this.name)
                .status(getLastStatusName())
                .location(getAnimalLocationName())
                .imageURL("")
                .build();
    }

    public AnimalDetailsResponse translateToAnimalDetailsResponse() {
        return AnimalDetailsResponse.builder()
                .id(this.id)
                .name(this.name)
                .species(Optional.ofNullable(this.species).isPresent() ? this.species.getName() : "")
                .race(Optional.ofNullable(this.race).isPresent() ? this.race.getName() : "")
                .status(getLastStatusName())
                .description(this.description)
                .birthDate(this.birthDate)
                .sex(this.sex)
                .sterilised(this.sterilised)
                .shelterJoinDate(this.shelterJoinDate)
                .animalLocation(getAnimalLocation())
                .treatmentHistories(this.treatmentHistories.stream().map(TreatmentHistory::translateToTreatmentHistoryResponse).collect(Collectors.toSet()))
                .build();
    }

    private String getLastStatusName() {
        Optional<Status> status = this.statuses.stream().filter(st -> st.getStatusEnd() == null).findFirst();
        return status.isPresent() ? status.get().getAnimalStatus().name() : "";
    }

    private String getAnimalLocationName() {
        Optional<Organization> organization = Optional.ofNullable(this.getUser().getOrganization());
        return organization.isPresent() ? organization.get().getAddress() : this.user.getAddress();
    }

    private AnimalLocationResponse getAnimalLocation() {
        Optional<Organization> organization = Optional.ofNullable(this.getUser().getOrganization());
        if (organization.isPresent()) {
            Organization org = organization.get();
            return AnimalLocationResponse.builder()
                    .fullName(org.getFullName())
                    .phone(org.getPhoneNumber())
                    .email(org.getEmail())
                    .address(org.getAddress())
                    .locationType(AnimalLocationType.ORGANIZATION)
                    .build();
        } else {
            return AnimalLocationResponse.builder()
                    .fullName(this.user.getFullName())
                    .phone(this.user.getPhoneNumber())
                    .email(this.user.getFullName())
                    .address(this.user.getAddress())
                    .locationType(AnimalLocationType.USER)
                    .build();
        }
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
