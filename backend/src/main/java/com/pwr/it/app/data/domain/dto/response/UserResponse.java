package com.pwr.it.app.data.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {

    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;

}
