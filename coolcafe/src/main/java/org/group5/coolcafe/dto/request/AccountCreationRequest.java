package org.group5.coolcafe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AccountCreationRequest {
    @NotNull
    String username;

    @NotNull
    String name;

    @NotNull
    String password;

    String email;

    @NotBlank
    String phoneNumber;
    String avatar;
    Boolean gender;
    LocalDate dateOfBirth;
}
