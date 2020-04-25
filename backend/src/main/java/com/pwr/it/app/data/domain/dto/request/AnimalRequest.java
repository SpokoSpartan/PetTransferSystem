package com.pwr.it.app.data.domain.dto.request;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@Introspected
public class AnimalRequest {

    @Size(max = 255, message = "Name cannot exceed 255 character")
    private String name;
    @Size(max = 255, message = "Species cannot exceed 255 character")
    private String species;
    @Size(max = 255, message = "Race cannot exceed 255 character")
    private String race;
    @Size(max = 1000, message = "Description cannot exceed 1000 character")
    private String description;
    @Past(message = "Animal cannot be born in the future")
    private Date birthDate;
    @Size(max = 255, message = "Sex cannot exceed 255 character")
    private String sex;
    private Boolean sterilised;

}
