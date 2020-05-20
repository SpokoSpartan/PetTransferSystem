package com.pwr.it.app.data.domain.dto.response;

import io.micronaut.core.annotation.Introspected;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Introspected
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnimalResponse implements Comparable<AnimalResponse> {

    private Long id;
    private String name;
    private String description;
    private String location;
    private String imageUrl;
    private String status;
    private Boolean archived;
    private AnimalLocationType locationType;

    @Override
    public int compareTo(AnimalResponse animalResponse) {
        return id.compareTo(animalResponse.getId());
    }

}
