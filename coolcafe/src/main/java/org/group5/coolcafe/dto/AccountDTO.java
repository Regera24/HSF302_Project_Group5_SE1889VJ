package org.group5.coolcafe.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.group5.coolcafe.enums.RoleEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDTO {
    String username, name, password, phoneNumber, email, avatar,gender,id;
    String role;
}