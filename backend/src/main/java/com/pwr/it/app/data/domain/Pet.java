package com.pwr.it.app.data.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet {

    @Id
    private UUID id;
    private String name;
    private Long age;

    public Pet(String name, Long age) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id.equals(((Pet) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
