package com.pwr.it.app.data.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
@Table(name = "\"user\"")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "users-organizations")
    private Organization organization;
    private String uuid = UUID.randomUUID().toString();

    public User(String fullName, String phoneNumber, String email, String address, Organization organization) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return uuid.equals(((User) o).uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
