package com.pwr.it.app.web.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class UserAccountDetails {

    @NotBlank(message = "Full name is required")
    private String fullName;
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "Password must contain minimum eight characters, at least one letter and one number")
    private String password;
    @Email(message = "Email need to be provided in correct format")
    @NotNull(message = "Email is required")
    private String email;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

}
