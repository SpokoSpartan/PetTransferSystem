package com.pwr.it.app.data.domain;

import com.pwr.it.app.web.dto.UserAccountDetails;
import io.micronaut.core.annotation.Creator;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Getter
@MappedEntity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccount {

    @Id
    private UUID id;
    private String login;
    private String password;
    private String role;
    private String address;
    private String phoneNumber;
    private String email;

    @Creator
    public UserAccount(UUID id, String login, String password, String role, String address, String phoneNumber, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UserAccount(String login, String password, String role, String address, String phoneNumber, String email) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UserAccountDetails translateToDetails() {
        return UserAccountDetails.builder()
                .fullName(this.login)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id.equals(((UserAccount) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
