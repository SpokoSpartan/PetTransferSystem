package com.pwr.it.app.data.domain.dto.request;

import com.pwr.it.app.data.domain.User;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class UserAccountDetails {

    private String fullName;
    private String password;
    private String email;
    private String address;
    private String phoneNumber;

    public User translateToUser() {
        return new User(fullName, phoneNumber, email, address);
    }

}
