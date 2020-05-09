package com.pwr.it.app.data.domain;

import com.pwr.it.app.data.domain.dto.response.*;
import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Animal {

    @Id
    @Setter(AccessLevel.NONE)
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
    @Setter(AccessLevel.NONE)
    private Date shelterJoinDate;
    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "animals-user")
    @Setter(AccessLevel.NONE)
    private User user;
    @OneToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "animals-treatment-history")
    @Setter(AccessLevel.NONE)
    private Set<TreatmentHistory> treatmentHistories = new HashSet<>();
    @Column(length = 500)
    private String imageUrl;
    @Setter(AccessLevel.NONE)
    private String uuid = UUID.randomUUID().toString();

    @Builder
    public Animal(String name, Species species, Race race, Set<Status> statuses, String description,
                  Date birthDate, String sex, Date shelterJoinDate, User user, String imageUrl) {
        this.name = name;
        this.species = species;
        this.race = race;
        this.statuses = statuses;
        this.description = description;
        this.birthDate = birthDate;
        this.sex = sex;
        this.shelterJoinDate = shelterJoinDate;
        this.user = user;
        this.imageUrl = imageUrl;
    }

    public void addTreatmentHistory(TreatmentHistory treatmentHistory) {
        this.treatmentHistories.add(treatmentHistory);
    }

    public AnimalResponse translateToAnimalResponse() {
        return AnimalResponse.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .status(getLastStatusName())
                .location(getAnimalLocationName())
                .locationType(getAnimalLocationType())
                .imageUrl(getImage())
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
                .imageUrl(getImage())
                .sterilised(this.sterilised)
                .shelterJoinDate(this.shelterJoinDate)
                .animalLocation(getAnimalLocation())
                .treatmentHistories(prepareTreatmentHistory())
                .build();
    }

    private String getLastStatusName() {
        Optional<Status> status = this.statuses.stream().filter(st -> st.getStatusEnd() == null).findFirst();
        return status.isPresent() ? status.get().getAnimalStatus().toString() : "";
    }

    private String getAnimalLocationName() {
        Optional<Organization> organization = getOrganization();
        return organization.isPresent() ? organization.get().getAddress() : this.user.getAddress();
    }

    private AnimalLocationType getAnimalLocationType() {
        Optional<Organization> organization = getOrganization();
        return organization.isPresent() ? AnimalLocationType.ORGANIZATION : AnimalLocationType.USER;
    }

    private AnimalLocationResponse getAnimalLocation() {
        Optional<Organization> organization = getOrganization();
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
                    .email(this.user.getEmail())
                    .address(this.user.getAddress())
                    .locationType(AnimalLocationType.USER)
                    .build();
        }
    }

    private Set<TreatmentHistoryResponse> prepareTreatmentHistory() {
        return this.treatmentHistories.stream()
                .map(TreatmentHistory::translateToTreatmentHistoryResponse)
                .collect(Collectors.toSet());
    }

    private Optional<Organization> getOrganization() {
        return Optional.ofNullable(this.getUser().getOrganization());
    }

    private String getImage() {
        return this.imageUrl != null ? this.imageUrl : "https://pbs.twimg.com/media/DOINwa5VQAUtkfh.jpg";
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
