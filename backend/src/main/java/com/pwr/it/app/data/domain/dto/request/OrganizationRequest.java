package com.pwr.it.app.data.domain.dto.request;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class OrganizationRequest {

    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;

}
