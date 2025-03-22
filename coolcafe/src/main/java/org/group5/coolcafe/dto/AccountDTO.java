package org.group5.coolcafe.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountDTO {
    String username;
    String name;
    String password;
    String email;
    String phoneNumber;
    String avatar;
    Boolean isActive;
    Boolean gender;
    LocalDate dateOfBirth;
}
