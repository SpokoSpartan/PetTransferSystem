package com.pwr.it.app.data.domain;

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

    @Creator
    public UserAccount(UUID id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UserAccount(String login, String password, String role) {
        this.id = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.role = role;
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
